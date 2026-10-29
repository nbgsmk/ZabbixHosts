<?php

$view = new CWidgetView($data);
$view
	->setVar('hosts', $data['hosts'])
	->setVar('map_config', $data['map_config'])
	->addItem((new CDiv())->addClass('host-group-icons-map'))
	->show();
