module cc.kostic.zbxhosts {
	requires javafx.controls;
	requires javafx.fxml;
	
	requires org.controlsfx.controls;
	requires com.dlsc.formsfx;
	requires org.jetbrains.annotations;
	requires java.xml;
	
	opens cc.kostic.zabbixhosts to javafx.fxml;
	exports cc.kostic.zabbixhosts;
}