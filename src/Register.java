import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;

public class Register {
	static long y;
	static String z;
	static File f = new File("data");
	static File g = new File("user");
	public static int ln;
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Register();
	}
	Register(){
		int tinggi = 420;
		int lebar = 420;
		int panjangteks = 250;
		int lebarteks = 30;
		
		JLabel judul = new JLabel("Welcome");
		judul.setBounds(lebar/4, 40, panjangteks, lebarteks+10);
		judul.setFont(new Font("Monospaced", Font.PLAIN, 50));
		
		JTextField nama = new JTextField();
		nama.setBounds(lebar/2-panjangteks/2, tinggi/4, panjangteks, lebarteks);
		nama.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		
		JLabel id = new JLabel("Username:");
		id.setBounds(5, nama.getY(), panjangteks/2, lebarteks);
		id.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JTextField saldo = new JTextField();
		saldo.setBounds(nama.getX(), nama.getY()+lebarteks*2, panjangteks, lebarteks);
		saldo.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		
		JLabel sal = new JLabel("Balance: ");
		sal.setBounds(5, saldo.getY(), panjangteks/2, lebarteks);
		sal.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JPasswordField sandi = new JPasswordField();
		sandi.setBounds(saldo.getX(), saldo.getY()+lebarteks*2, panjangteks, lebarteks);
		sandi.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		
		JLabel pass = new JLabel("Password: ");
		pass.setBounds(5, sandi.getY(), panjangteks/2, lebarteks);
		pass.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JButton daftar = new JButton("Daftarkan");
		daftar.setBounds(sandi.getX()+panjangteks/4, sandi.getY()+lebarteks+10, panjangteks/2, lebarteks);
		daftar.setBackground(new Color(203, 206, 243));
		daftar.setFocusable(false);
		
		JButton login = new JButton("Sudah Punya Akun");
		login.setBounds(sandi.getX(), daftar.getY()+lebarteks+5, panjangteks, lebarteks);
		login.setBackground(new Color(203, 206, 243));
		login.setFocusable(false);
		
		JCheckBox cek = new JCheckBox();
		cek.setText("show");
		cek.setBounds(sandi.getX()+sandi.getWidth(), sandi.getY(), 75,30);
		cek.setBackground(new Color(203, 206, 243));
		cek.setFocusable(false);

		
		JButton reset = new JButton("Reset");
		reset.setBounds(login.getX()+panjangteks/3, login.getY()+lebarteks+5, panjangteks/3, lebarteks);
		reset.setBackground(new Color(203, 206, 243));
		reset.setFocusable(false);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(lebar,tinggi));
		panel.setBackground(new Color(203, 206, 243));
		panel.setLayout(null);
		panel.add(nama);
		panel.add(sandi);
		panel.add(saldo);
		panel.add(login);
		panel.add(daftar);
		panel.add(reset);
		panel.add(cek);
		panel.add(id);
		panel.add(pass);
		panel.add(sal);
		panel.add(judul);
		
		JFrame frame = new JFrame("Daftar Akun");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		//cek ActionListener
		cek.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(cek.isSelected()) {
					sandi.setEchoChar((char)0);
				}else {
					sandi.setEchoChar((char) UIManager.get("PasswordField.echoChar"));
				}
			}
		});
		//reset ActionListener
		reset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				nama.setText("");
				saldo.setText("");
				sandi.setText("");
			}
		});
		//Login ActionListener
				login.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						new Main();
						frame.dispose();
						
					}
				});
				//Daftar ActionListener
				daftar.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						if(e.getSource()==daftar) {
							createFolder2();
							readFile();
							countLine();
							rek();
							addData(nama.getText(), String.valueOf(sandi.getPassword()),z);
							register(nama.getText(), saldo.getText(), z);
						}
					}
				});
	}
	//Untuk Nomor Rekening
	static void rek() {
		Random random = new Random();
		y = random.nextInt(1000000000)+1;
		System.out.println(y);
		
		z = String.valueOf(y);
	}
	public static void createFolder() {
		if(!f.exists()) {
			f.mkdirs();
		}
	}
	public static void createFolder2() {
		if(!g.exists()) {
			g.mkdirs();
		}
	}
	public static void countLine() {
		try {
			ln = 0;
			RandomAccessFile raf = new RandomAccessFile(f+"\\login.txt", "rw");
			for(int i = 0; raf.readLine()!= null; i++) {
				ln++;
			}
		} catch (Exception e) {
		}
	}
	static void readFile() {
		try {
			FileReader file  = new FileReader(f+"\\login.txt");
		} catch (Exception e) {
			try {
				FileWriter fw = new FileWriter(f+"\\login.txt");
			} catch (IOException e1) {
			}
		}
	}
	public static void addData(String usr, String pass, String ser) {
		try {
			RandomAccessFile raf = new RandomAccessFile(f+"\\login.txt", "rw");
			for(int i = 0; i<ln;i++) {
				raf.readLine();
			}
			if(ln>0) {
				raf.writeBytes("\r\n");
				raf.writeBytes("\r\n");
			}
			raf.writeBytes("Username:"+usr+"\r\n");
			raf.writeBytes("Password:"+pass+"\r\n");
			raf.writeBytes("Data Ser:"+ser+"\r\n");
		} catch (Exception e) {
		}
	}
	public static void register(String usr, String saldo, String rek) {
		DataUser user = new DataUser();
		user.nama= usr;
		user.saldo = saldo;
		FileOutputStream fileout;
		File file = new File(String.valueOf(g+"\\"+rek+".ser"));
        if (file.exists()) {
            JOptionPane.showMessageDialog(null, "Gunakan Pin Yang Lain", "Warning", JOptionPane.WARNING_MESSAGE, null);
        }else {
        	try {
				fileout = new FileOutputStream(String.valueOf(g+"\\"+rek+".ser"));
				ObjectOutputStream out = new ObjectOutputStream(fileout);
				out.writeObject(user);
				out.close();
				JOptionPane.showMessageDialog(null, "Daftar Berhasil");
			} catch (Exception e1) {
			}
        }
	}
}