# Hosts by group map widget

This is a Zabbix 8.0 frontend widget module. Copy the `host_group_icons` directory
to `<zabbix-frontend>/modules/`, then register and enable it under **Administration
> General > Modules**. Add **Hosts by group map** to a dashboard.

The map uses each host's latitude and longitude from **Inventory** and the tile
provider configured in **Administration > General > Geographical maps**. Hosts
without valid coordinates are omitted. Map markers use a distinct icon for the
alphabetically first group associated with the host; clicking a marker shows all
its groups. Use **Host groups** to filter hosts. **Icons per host group** accepts
one `group name=icon` mapping per line. Unmapped groups receive distinct fallback
icons automatically.
