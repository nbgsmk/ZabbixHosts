class WidgetHostGroupIcons extends CWidget {

	onInitialize() {
		this._map = null;
		this._markers = null;
		this._fitted_hosts = false;
	}

	onResize() {
		if (this._map !== null) {
			this._map.invalidateSize();
		}
	}

	onDestroy() {
		if (this._map !== null) {
			this._map.remove();
			this._map = null;
		}
	}

	setContents(response) {
		if (this._map === null) {
			super.setContents(response);
		}

		const container = this._body.querySelector('.host-group-icons-map');

		if (container === null) {
			this._body.textContent = 'The map container could not be created.';
			return;
		}

		if (typeof L === 'undefined') {
			container.classList.add('host-group-icons-map-error');
			container.textContent = 'Leaflet is unavailable in this Zabbix frontend.';
			return;
		}

		if (this._map === null) {
			this._map = L.map(container);
			this._markers = L.layerGroup().addTo(this._map);

			if (response.map_config.tile_url !== '') {
				L.tileLayer(response.map_config.tile_url, {
					minZoom: 0,
					maxZoom: Number.parseInt(response.map_config.max_zoom, 10),
					attribution: response.map_config.attribution
				}).addTo(this._map);
			}
		}

		this._markers.clearLayers();
		const bounds = [];

		for (const host of response.hosts) {
			const markerElement = document.createElement('span');
			markerElement.className = 'host-group-icons-marker';
			markerElement.setAttribute('aria-hidden', 'true');

			const iconText = document.createElement('span');
			iconText.textContent = host.map_icon;
			markerElement.append(iconText);

			const groupNames = host.hostgroups.map((group) => group.name).join(', ');
			const marker = L.marker([host.latitude, host.longitude], {
				icon: L.divIcon({
					className: '',
					html: markerElement,
					iconSize: [34, 34],
					iconAnchor: [17, 34],
					popupAnchor: [0, -32]
				}),
				title: `${host.name} (${groupNames})`
			});
			markerElement.classList.add(`host-group-icons-severity-${host.max_severity}`);

			const popup = document.createElement('div');
			const hostName = document.createElement('strong');
			hostName.textContent = host.name;
			popup.append(hostName);

			if (host.hostgroups.length > 0) {
				const groups = document.createElement('div');
				groups.className = 'host-group-icons-popup-groups';
				groups.textContent = host.hostgroups.map((group) => `${group.icon} ${group.name}`).join(', ');
				popup.append(groups);
			}

			const details = document.createElement('div');
			details.className = 'host-group-icons-popup-details';

			const mainInterface = host.interfaces?.find((iface) => iface.main == 1)
				?? host.interfaces?.[0];
			const inventoryType = host.inventory?.type;
			const ipAddress = mainInterface?.ip || mainInterface?.dns;
			const detailRows = [
				['IP address', ipAddress],
				['Inventory type', inventoryType],
				['Ping latency', host.ping?.lastvalue
					? `${(Number(host.ping.lastvalue) * 1000).toFixed(2)} ms`
					: 'Unavailable']
			];

			for (const [label, value] of detailRows) {
				const row = document.createElement('div');
				row.textContent = `${label}: ${value || 'Unavailable'}`;
				details.append(row);
			}
			popup.append(details);

			const severityNames = ['Not classified', 'Information', 'Warning', 'Average', 'High', 'Disaster'];
			const severity = Number(host.max_severity || 0);
			const severityRow = document.createElement('div');
			severityRow.textContent = `Severity: ${severityNames[severity] || severity}`;
			details.append(severityRow);

			const problemsLink = document.createElement('a');
			problemsLink.href = `zabbix.php?action=problem.view&filter_hostids[]=${encodeURIComponent(host.hostid)}`;
			problemsLink.textContent = 'Open Zabbix problems';
			problemsLink.target = '_blank';
			problemsLink.rel = 'noopener';
			popup.append(problemsLink);

			marker.bindPopup(popup);
			marker.addTo(this._markers);
			bounds.push([host.latitude, host.longitude]);
		}

		if (!this._fitted_hosts) {
			if (bounds.length > 1) {
				this._map.fitBounds(bounds, {padding: [24, 24]});
				this._fitted_hosts = true;
			}
			else if (bounds.length === 1) {
				this._map.setView(bounds[0], 13);
				this._fitted_hosts = true;
			}
			else {
				this._map.setView([0, 0], 1);
			}
		}

		this._map.invalidateSize();
	}
}
