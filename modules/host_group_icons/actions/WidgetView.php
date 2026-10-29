<?php

namespace Modules\HostGroupIcons\Actions;

use API;
use CControllerDashboardWidgetView;
use CControllerResponseData;
use CSettingsHelper;

class WidgetView extends CControllerDashboardWidgetView {

	private const FALLBACK_ICONS = [
		'🖥️', '🛜', '🛰️', '🧭', '🏭', '🚦', '🔌', '💡', '🛡️', '📡', '🗄️', '🔧',
		'🧰', '🚨', '🌍', '🔒', '📶', '⚡', '🧪', '📍', '🧱', '🧲', '🛠️', '🧯'
	];

	protected function doAction(): void {
		$groupids = array_values(array_filter(
			$this->fields_values['groupids'] ?? [],
			static fn($groupid): bool => ctype_digit((string) $groupid)
		));

		$params = [
			'output' => ['hostid', 'name'],
			'selectHostGroups' => ['groupid', 'name'],
			'selectInventory' => ['location_lat', 'location_lon', 'type'],
			'selectInterfaces' => ['ip', 'dns', 'port', 'main', 'type'],
			'filter' => [
				'inventory_mode' => [HOST_INVENTORY_MANUAL, HOST_INVENTORY_AUTOMATIC]
			],
			'monitored_hosts' => true,
			'sortfield' => 'name',
			'sortorder' => 'ASC'
		];

		$filtered_groupids = $groupids !== [] ? getSubGroups($groupids) : [];
		if ($filtered_groupids !== []) {
			$params['groupids'] = $filtered_groupids;
		}

		$hosts = API::Host()->get($params);
		$ping_items = [];
		if ($hosts !== []) {
			$items = API::Item()->get([
				'output' => ['hostid', 'lastvalue', 'units'],
				'hostids' => array_column($hosts, 'hostid'),
				'filter' => ['key_' => ['icmppingsec']],
				'monitored' => true
			]);
			foreach ($items as $item) {
				$ping_items[$item['hostid']] = [
					'lastvalue' => $item['lastvalue'],
					'units' => $item['units']
				];
			}
		}
		$group_icons = $this->getGroupIcons($hosts, $this->fields_values['group_icons'] ?? '');
		$map_hosts = [];

		foreach ($hosts as $host) {
			$inventory = $host['inventory'] ?? [];
			$latitude = $inventory['location_lat'] ?? null;
			$longitude = $inventory['location_lon'] ?? null;
			if (!is_numeric($latitude) || !is_numeric($longitude)
					|| $latitude < -90 || $latitude > 90 || $longitude < -180 || $longitude > 180) {
				continue;
			}

			$host['hostgroups'] = $host['hostgroups'] ?? [];
			foreach ($host['hostgroups'] as &$group) {
				$group['icon'] = $group_icons[$group['groupid']];
			}
			unset($group);

			usort($host['hostgroups'], static fn(array $a, array $b): int =>
				strnatcasecmp($a['name'], $b['name'])
			);
			$marker_groups = $filtered_groupids === []
				? $host['hostgroups']
				: array_values(array_filter(
					$host['hostgroups'],
					static fn(array $group): bool => in_array($group['groupid'], $filtered_groupids)
				));
			$host['latitude'] = (float) $latitude;
			$host['longitude'] = (float) $longitude;
			$host['ping'] = $ping_items[$host['hostid']] ?? null;
			$host['map_icon'] = $marker_groups[0]['icon'] ?? '📍';
			$map_hosts[] = $host;
		}

		$this->setResponse(new CControllerResponseData([
			'name' => $this->getInput('name', $this->widget->getDefaultName()),
			'hosts' => $map_hosts,
			'map_config' => self::getMapConfig()
		]));
	}

	private function getGroupIcons(array $hosts, string $configured_icons): array {
		$groups = [];

		foreach ($hosts as $host) {
			foreach ($host['hostgroups'] ?? [] as $group) {
				$groups[$group['groupid']] = $group['name'];
			}
		}

		asort($groups, SORT_NATURAL | SORT_FLAG_CASE);

		$configured = [];
		foreach (preg_split('/\R/', $configured_icons) as $line) {
			$separator = strpos($line, '=');
			if ($separator === false) {
				continue;
			}

			$name = trim(substr($line, 0, $separator));
			$icon = trim(substr($line, $separator + 1));
			if ($name !== '' && $icon !== '') {
				$configured[$name] = $icon;
			}
		}

		$icons = [];
		$used_icons = [];
		foreach ($groups as $groupid => $name) {
			if (isset($configured[$name]) && !isset($used_icons[$configured[$name]])) {
				$icons[$groupid] = $configured[$name];
				$used_icons[$configured[$name]] = true;
			}
		}

		$icon_index = 0;
		foreach ($groups as $groupid => $name) {
			if (isset($icons[$groupid])) {
				continue;
			}

			while (isset(self::FALLBACK_ICONS[$icon_index])
					&& isset($used_icons[self::FALLBACK_ICONS[$icon_index]])) {
				$icon_index++;
			}

			$icon = self::FALLBACK_ICONS[$icon_index] ?? '⭐'.($icon_index + 1);
			$icons[$groupid] = $icon;
			$used_icons[$icon] = true;
			$icon_index++;
		}

		return $icons;
	}

	private static function getMapConfig(): array {
		if (CSettingsHelper::get(CSettingsHelper::GEOMAPS_TILE_PROVIDER) === '') {
			return [
				'tile_url' => CSettingsHelper::get(CSettingsHelper::GEOMAPS_TILE_URL),
				'max_zoom' => CSettingsHelper::get(CSettingsHelper::GEOMAPS_MAX_ZOOM),
				'attribution' => htmlspecialchars(
					CSettingsHelper::get(CSettingsHelper::GEOMAPS_ATTRIBUTION),
					ENT_NOQUOTES,
					'UTF-8'
				)
			];
		}

		$tile_providers = getTileProviders();
		$provider_id = CSettingsHelper::get(CSettingsHelper::GEOMAPS_TILE_PROVIDER);
		$provider = $tile_providers[$provider_id] ?? reset($tile_providers);

		return [
			'tile_url' => $provider['geomaps_tile_url'],
			'max_zoom' => $provider['geomaps_max_zoom'],
			'attribution' => $provider['geomaps_attribution']
		];
	}
}
