module cc.kostic.zbxhosts {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.jetbrains.annotations;
	requires java.xml;
	
	opens cc.kostic.zabbixhosts to javafx.fxml;
	exports cc.kostic.zabbixhosts;
	exports cc.kostic.zabbixhosts.metadata;
	opens cc.kostic.zabbixhosts.metadata to javafx.fxml;

	opens cc.kostic.zabbixhosts.datamodel to javafx.fxml;
	exports cc.kostic.zabbixhosts.datamodel;

}