import java.awt.event.*;
import javax.swing.*;
import java.io.*;
import java.net.*;
import java.sql.Driver;

public class MyGUI{
	private JButton run = new JButton("运行");
	private JTextField t1, t2;
	private JTextArea ta2= new JTextArea(20,10);
	private JScrollPane jsp2 = new JScrollPane(ta2);
	private JLabel l1, l2;
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
    private JFrame jf = new JFrame("MAP");    
	MyGUI() {
		JPanel jp = new JPanel();
		l1 = new JLabel("起点");
		l2 = new JLabel("终点");
		t1 = new JTextField(10);
		t2 = new JTextField(10);
		jp.add(l1);
		jp.add(t1);
		jp.add(l2);
		jp.add(t2);
		jp.add(run);
		jp2.add(jsp2);
		jf.add(jp, "North");
		jf.add(jp1, "West");	
		jf.add(jp2,"East");
		//jp1.repaint();
		MyActionListener al = new MyActionListener();
		run.addActionListener(al);
		jf.setBounds(50,50,800,650);
		jf.setVisible(true);
		jf.setResizable(false);
	}

	class MyActionListener implements ActionListener{
		private String origin, destination,origin_Address, destination_Address;
		public void actionPerformed(ActionEvent e1) {
			if (e1.getSource() == run) {
				origin = t1.getText();
				origin_Address = origin;
				destination_Address = destination;
				destination = t2.getText();
				try {
					jp2.removeAll();
				    jp1.removeAll();
					origin = Map.get_Lngtdd_Lattd(origin);
					destination = Map.get_Lngtdd_Lattd(destination);
					Map.get_Line_ZuoBiao(origin, destination);
					Map.get_Line_Message(origin, destination);					
					FileInputStream fs = new FileInputStream("路径坐标.txt");
					BufferedReader bf = new BufferedReader(new InputStreamReader(fs));
					String sb = "";	
					StringBuilder sc = new StringBuilder("");
						while((sb = bf.readLine()) != null) {
								sc.append(sb +";");
						}					
				    sb = sc.toString();
					System.out.println(sb.substring(0, sb.length()-1));
					System.out.println(URLEncoder.encode(origin_Address, "UTF-8")+",2,0,16,0xFFFFFF,0x008000:"+origin);
					URL url = new URL("http://restapi.amap.com/v3/staticmap?size=600*600&paths=5,0x0000ff,1,,:"+ URLEncoder.encode(sb.substring(0, sc.length()-1), "UTF-8")+ "&key=e55a843992773d15743660029ccf2f9a");
					BufferedInputStream input = new BufferedInputStream(url.openStream());
					ByteArrayOutputStream output = new ByteArrayOutputStream();
					int k;
					while ((k = input.read()) != -1) {
						output.write((byte) k);
					}
					input.close();
					bf.close();
					JLabel jl = new JLabel((new ImageIcon(output.toByteArray())));
					jp1.add(jl);
					jp1.updateUI();
					jp1.repaint();//重新绘制地图面板
					BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream("路径信息.txt")));					
					String se = "";
					StringBuilder sd = new StringBuilder("");
					while((se = br.readLine()) != null){											
						sd.append(se+"\n");					
					}br.close();
					ta2.setText(sd.toString());
					jp2.add(jsp2);
					jp2.updateUI();
					jp2.repaint();//绘制路径信息输出框
				} catch (Exception e2) {
					System.out.println("\ne2:"+e2);
				}
			}			
		}
	}
	public String getT1() {
		return t1.getText();
	}

	public String getT2() {
		return t2.getText();
	}
	public static void main(String[] args) {
		MyGUI a = new MyGUI();
	}
}
