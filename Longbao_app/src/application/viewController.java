package application;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.swing.text.TabableView;

import com.dang.domain.DataTable;
import com.dang.domain.MySetting;
import com.dang.domain.PropertySetting;
import com.dang.service.ExportExcel;
import com.dang.service.GetData;
import com.dang.service.SaveData;
import com.dang.service.SettingService;
import com.dang.util.Setting;
import com.dang.util.Tools;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;

public class viewController implements Initializable{

	//dataTable
	@FXML
	private TextField chaohua_qiandao;
	@FXML
	private TextField chaohua_tiezi;
	@FXML
	private TextField mingxing_hudong;
	@FXML
	private TextField mingxing_tiji;
	@FXML
	private TextField mingxing_aimu;
	@FXML
	private TextField mingxing_sousuo;
	@FXML
	private TextField tieba_qiandao;
	@FXML
	private TextField mingxing_zongfen;
	@FXML
	private TextField baidu_songhua;
	@FXML
	private TextField tieba_tiezi;
	@FXML
	private TextField xunyi_qiandao;
	@FXML
	private TextField vbang_zhishu;
	@FXML
	private TextField vbang_paiming;
	@FXML
	private TextField xunyi_paiming;
	@FXML
	private TextField baidu_songhua_paiming;
	@FXML
	private TextField mingxing_zongfen_paiming;
	@FXML
	private TextField tieba_paiming;
	@FXML
	private TextField mingxing_sousuo_paiming;
	@FXML
	private TextField mingxing_aimu_paiming;
	@FXML
	private TextField mingxing_tiji_paiming;
	@FXML
	private TextField mingxing_hudong_paiming;
	@FXML
	private TextField chaohua_paiming;
	@FXML
	private TextField stateInfo;
	
	//settingTable
	@FXML
	private TextField savePath;
	@FXML
	private TextField weiboNumber;
	@FXML
	private TextField username;
	@FXML
	private TextField password;
	@FXML
	private TableView<PropertySetting> weiboTable;
	@FXML
	private TableColumn<PropertySetting, String> number;
	@FXML
	private TableColumn<PropertySetting, String> Weiboname;
	@FXML
	private TableColumn<PropertySetting, String> Weibopass;

	// Event Listener on Button.onAction
	@FXML
	public void getData(ActionEvent event) {
		DataTable dataTable = new DataTable();
		MySetting mySetting = Setting.readSetting();
		dataTable = GetData.getData(mySetting);
		setDataTable(dataTable);
		stateInfo.setText("��ȡ������");
	}
	private void setDataTable(DataTable dataTable) {

		CheckAndSet(chaohua_qiandao,dataTable.getChaohua_qiandao());
		CheckAndSet(chaohua_tiezi,dataTable.getChaohua_tiezi());
		CheckAndSet(mingxing_hudong,dataTable.getMingxing_hudong());
		CheckAndSet(mingxing_tiji,dataTable.getMingxing_tiji());
		CheckAndSet(mingxing_aimu,dataTable.getMingxing_aimu());
		CheckAndSet(mingxing_sousuo,dataTable.getMingxing_sousuo());
		CheckAndSet(tieba_qiandao,dataTable.getTieba_qiandao());
		CheckAndSet(mingxing_zongfen,dataTable.getMingxing_zongfen());
		CheckAndSet(baidu_songhua,dataTable.getBaidu_songhua());
		CheckAndSet(tieba_tiezi,dataTable.getTieba_tiezi());
		CheckAndSet(xunyi_qiandao,dataTable.getXunyi_qiandao());
		CheckAndSet(vbang_zhishu,dataTable.getVbang_zhishu());
//		CheckAndSet(sougou_toupiao,dataTable.getSougou_toupiao());
//		CheckAndSet(sougou_paiming,dataTable.getSougou_paiming());
		CheckAndSet(vbang_paiming,dataTable.getVbang_paiming());
		CheckAndSet(xunyi_paiming,dataTable.getXunyi_paiming());
		CheckAndSet(baidu_songhua_paiming,dataTable.getBaidu_songhua_paiming());
		CheckAndSet(mingxing_zongfen_paiming,dataTable.getMingxing_zongfen_paiming());
		CheckAndSet(tieba_paiming,dataTable.getTieba_paiming());
		CheckAndSet(mingxing_sousuo_paiming,dataTable.getMingxing_sousuo_paiming());
		CheckAndSet(mingxing_aimu_paiming,dataTable.getMingxing_aimu_paiming());
		CheckAndSet(mingxing_tiji_paiming,dataTable.getMingxing_tiji_paiming());
		CheckAndSet(mingxing_hudong_paiming,dataTable.getMingxing_hudong_paiming());
		CheckAndSet(chaohua_paiming,dataTable.getChaohua_paiming());
	}
	// Event Listener on Button.onAction
	@FXML
	public void saveData(ActionEvent event) {
		DataTable dataTable = new DataTable();
		DataTable saveDataTable = new DataTable();
		saveDataTable.setBaidu_songhua(baidu_songhua.getText());
		saveDataTable.setBaidu_songhua_paiming(baidu_songhua_paiming.getText());
		saveDataTable.setChaohua_paiming(chaohua_paiming.getText());
		saveDataTable.setChaohua_qiandao(chaohua_qiandao.getText());
		saveDataTable.setChaohua_tiezi(chaohua_tiezi.getText());
		saveDataTable.setMingxing_aimu(mingxing_aimu.getText());
		saveDataTable.setMingxing_aimu_paiming(mingxing_aimu_paiming.getText());
		saveDataTable.setMingxing_hudong(mingxing_hudong.getText());
		saveDataTable.setMingxing_hudong_paiming(mingxing_hudong_paiming.getText());
		saveDataTable.setMingxing_sousuo(mingxing_sousuo.getText());
		saveDataTable.setMingxing_sousuo_paiming(mingxing_sousuo_paiming.getText());
		saveDataTable.setMingxing_tiji(mingxing_tiji.getText());
		saveDataTable.setMingxing_tiji_paiming(mingxing_tiji_paiming.getText());
		saveDataTable.setMingxing_zongfen(mingxing_zongfen.getText());
		saveDataTable.setMingxing_zongfen_paiming(mingxing_zongfen_paiming.getText());
		saveDataTable.setTieba_paiming(tieba_paiming.getText());
		saveDataTable.setTieba_qiandao(tieba_qiandao.getText());
		saveDataTable.setTieba_tiezi(tieba_tiezi.getText());
		saveDataTable.setVbang_paiming(vbang_paiming.getText());
		saveDataTable.setVbang_zhishu(vbang_zhishu.getText());
		saveDataTable.setXunyi_paiming(xunyi_paiming.getText());
		saveDataTable.setXunyi_qiandao(xunyi_qiandao.getText());
		boolean flag = SaveData.saveData(saveDataTable);
		if(flag)
			stateInfo.setText("����ɹ���");
		else
			stateInfo.setText("���治�ɹ���(����пջ����ݿ�δ����)");
		System.out.println("�������ˡ�");
	}
	// Event Listener on Button.onAction
	@FXML
	public void exportExcel(ActionEvent event) {
		String path = Setting.readSetting().getPath();
		boolean flag = ExportExcel.export(path);
		//��path
		if(flag)
		{
			stateInfo.setText("�ļ��ѵ�������"+path);
			System.out.println("�����ɹ�");
		}
		else 
		{
			stateInfo.setText("����ʧ�ܣ��Ҳ���·�������ļ��Ѵ򿪣�");
			System.out.println("����ʧ��");
		}
			
	}
	
//	@FXML
//	public void SettingOK(ActionEvent event) {
//		//set weibo 
//		//set save path
//	}
//	// Event Listener on Button.onAction
//	@FXML
//	public void exportSetting(ActionEvent event) {
//		
//	}
	@FXML
	public void addWeibo(ActionEvent event){
		String newname = username.getText();
		String newpass = password.getText();
		MySetting mySetting = Setting.readSetting();
		SettingService.addWeibo(newname, newpass,mySetting);
		Setting.saveSetting(mySetting);
		showSetting(mySetting);
		username.setText("");
		password.setText("");
		System.out.println("��ӳɹ�");
		stateInfo.setText("��ӳɹ�");
		
	}
	@FXML
	public void removeWeibo(ActionEvent event){
		int num = Integer.parseInt(weiboNumber.getText());
		MySetting mySetting = Setting.readSetting();
		SettingService.removeWeibo(num,mySetting);
		Setting.saveSetting(mySetting);
		showSetting(mySetting);
		weiboNumber.setText("");
		System.out.println("ɾ���ɹ�");
		stateInfo.setText("ɾ���ɹ�");
	}
	private void showSetting(MySetting mySetting)
	{
		ObservableList<PropertySetting> list = FXCollections.observableArrayList();  
		ArrayList<String> name = mySetting.getUsername();
		ArrayList<String> pass = mySetting.getPassword();
		for(int i=1;i<name.size();i++)
		{
			PropertySetting ps = new PropertySetting();
			ps.setId((i)+"");
			ps.setUsername(name.get(i));
			ps.setPassword(pass.get(i));
			list.add(ps);
		}
		weiboTable.setItems(list);
	}
	private void CheckAndSet(TextField textField,String data)
	{
		if(data!=null&&data!=null&&data!="0")
		{
			textField.setText(data);
		}
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		// TODO Auto-generated method stub
		//��ѯ���ݿ⣬����Ѿ��е��յ����ݣ�ֱ�Ӷ�����
		DataTable today = Tools.getDataTable(Calendar.getInstance().getTime());
		if(today!=null)
		{
			setDataTable(today);
		}
		
		number.setCellValueFactory(cellData -> cellData.getValue().idProperty());
		Weiboname.setCellValueFactory(cellData -> cellData.getValue().usernameProperty());
		Weibopass.setCellValueFactory(cellData -> cellData.getValue().passwordProperty());
		MySetting mySetting = Setting.readSetting();
		showSetting(mySetting);
		
		savePath.setText(mySetting.getPath());
		
	}
	@FXML
	public void pathOK(ActionEvent event){
		String path = savePath.getText();
		MySetting ms = Setting.readSetting();
		ms.setPath(path);
		Setting.saveSetting(ms);
		System.out.println("����·���ɹ�");
		stateInfo.setText("����·���ɹ�");
		
	}
}
