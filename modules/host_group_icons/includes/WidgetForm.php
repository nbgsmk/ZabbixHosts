<?php

namespace Modules\HostGroupIcons\Includes;

use Zabbix\Widgets\CWidgetForm;
use Zabbix\Widgets\Fields\CWidgetFieldMultiSelectGroup;
use Zabbix\Widgets\Fields\CWidgetFieldTextArea;

class WidgetForm extends CWidgetForm {

	public const DEFAULT_GROUP_ICONS = <<<'ICONS'
grp_3G=📶
grp_Agregati=⚙️
grp_DVBT=📺
grp_HCLink=🔗
grp_IPLink=🌐
grp_NemaPristup=🚫
grp_UPS=🔋
grp_Velikih11=🏢
grp_MUX1=🎛️
grp_MUX2=🎚️
grp_MUX3=📡
ICONS;

	public function addFields(): self {
		return $this
			->addField(
				new CWidgetFieldMultiSelectGroup('groupids', _('Host groups'))
			)
			->addField(
				(new CWidgetFieldTextArea('group_icons', _('Icons per host group')))
					->setDefault(self::DEFAULT_GROUP_ICONS)
			);
	}
}
