import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.swing.*;
import javax.swing.text.DefaultFormatter;

public class Main {
	static File f = new File("data");
	static File g = new File("user");
	public static String nama;
	public static String pass;
	public static String ser;
	public static int ln;
	static String forrek;
	static long sisa=0;
	
	ImageIcon image = new ImageIcon("assets\\Bank.png");
	
	public static void main(String[] args) {
		new Main();
	}
	Main(){
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
		
		JPasswordField sandi = new JPasswordField();
		sandi.setBounds(nama.getX(), nama.getY()+lebarteks*2, panjangteks, lebarteks);
		sandi.setFont(new Font("Comic Sans", Font.PLAIN, 20));
		
		JLabel pass = new JLabel("Password: ");
		pass.setBounds(5, sandi.getY(), panjangteks/2, lebarteks);
		pass.setFont(new Font("Monospaced", Font.PLAIN, 15));
		
		JCheckBox cek = new JCheckBox();
		cek.setText("show");
		cek.setBounds(sandi.getX()+sandi.getWidth(), sandi.getY(), 75,30);
		cek.setBackground(new Color(203, 206, 243));
		cek.setFocusable(false);
		
		JButton login = new JButton("Login");
		login.setBounds(sandi.getX(), sandi.getY()+lebarteks*2, panjangteks/2, lebarteks);
		login.setBackground(new Color(203, 206, 243));
		login.setFocusable(false);
		
		JButton daftar = new JButton("Daftar");
		daftar.setBounds(login.getX()+panjangteks/2, login.getY(), panjangteks/2, lebarteks);
		daftar.setBackground(new Color(203, 206, 243));
		daftar.setFocusable(false);
		
		JButton reset = new JButton("Reset");
		reset.setBounds(login.getX()+panjangteks/3, login.getY()+lebarteks+20, panjangteks/3, lebarteks);
		reset.setBackground(new Color(203, 206, 243));
		reset.setFocusable(false);
		
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(lebar,tinggi));
		panel.setBackground(new Color(203, 206, 243));
		panel.setLayout(null);
		panel.add(nama);
		panel.add(sandi);
		panel.add(login);
		panel.add(daftar);
		panel.add(cek);
		panel.add(reset);
		panel.add(id);
		panel.add(pass);
		panel.add(judul);

		
		JFrame frame = new JFrame("Bank Account");
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.setIconImage(image.getImage());
		frame.add(panel);
		frame.pack();
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		//reset ActionListener
		reset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				nama.setText("");
				sandi.setText("");
			}
		});
		//Menampilakan Sandi (cek)ActionListener
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
		//Login ActionListener
		login.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				createFolder();
				readFile();
				countLine();
				logic(nama.getText(), String.valueOf(sandi.getPassword()));
				transaksi(forrek, nama.getText(), String.valueOf(sandi.getPassword()));
				//System.out.println(forrek);
				//new Transaksi(forrek);
				nama.setText("");
				sandi.setText("");
			}
		});
		//Daftar ActionListener
		daftar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				if(e.getSource()==daftar) {
					new Register();
					frame.dispose();
				}
			}
		});
	}
	public static void transfer(long saldo, long sisa2, int batasTransaksi, int aa) throws Exception {
		String rek = JOptionPane.showInputDialog("Masukan No Rekening Tujuan Anda: ");
		File file = new File(g+"\\"+rek+".ser");
		if(file.exists()==true) {
			FileInputStream filein = new FileInputStream(g+"\\"+rek+".ser");
			ObjectInputStream in = new ObjectInputStream(filein);
	        DataUser user3 = (DataUser)in.readObject();
	        in.close();
	        long saldoTujuan = Long.parseLong(user3.saldo);
	        long jumlah = Long.parseLong(JOptionPane.showInputDialog("Masukan Jumlah Transfer"));
	        if (jumlah<saldo && jumlah>=50000) {
	            JOptionPane.showMessageDialog(null,"Anda Akan Melakukan Transfer Saldo ke No Rekening\n"+rek+"\nDengan Jumlah"+"\nRp."+jumlah);                            
	            String valid = JOptionPane.showInputDialog("Apakah informasi tersebut telah benar adanya?\n"+"[y/n]");
	            if (valid.equalsIgnoreCase("y")) {
	            	saldoTujuan = saldoTujuan + jumlah;
	                sisa2 = saldo - jumlah;
	                JOptionPane.showMessageDialog(null, "Transaksi Berhasil\n" + "Sisa Saldo Anda sebanyak: Rp. " + sisa2);
	                saldo = sisa2;                                                                                               
	            }else if (valid.equalsIgnoreCase("n")) {
	                batasTransaksi = 0;
	                aa=0;                                                                   
	            }else {
	                batasTransaksi = 0; 
	                aa=0;
	            }                            
	        }else {
	            JOptionPane.showMessageDialog(null, "Transaksi tidak bisa dilanjukan.\n"+"Saldo Kurang/ Jumlah Transfer Tidak Mencapai batas Minimum,-"+ "Sisa saldo Anda sebanyak: Rp."+ saldo);
	        }
	        DataUser user4 = new DataUser();
	        user4.nama= user3.nama;
			user4.saldo = String.valueOf(saldoTujuan);
			FileOutputStream fileout;
	        try {
				fileout = new FileOutputStream(String.valueOf(g+"\\"+rek+".ser"));
				ObjectOutputStream out = new ObjectOutputStream(fileout);
				out.writeObject(user4);
				out.close();
			} catch (Exception e1) {
			}
		}else if(!file.exists()==true) {
			JOptionPane.showMessageDialog(null, "Rekening Tujuan Anda tidak terdaftar!!\n"+"Silahkan Ulangi Kembali");
		}
		System.out.println(sisa2);
		sisa = saldo;
	}
	public static void setorTunai(String b, long saldo) {
		long saldoAkhir=0;
		FileInputStream filein;
		try {
			filein = new FileInputStream(g+"\\"+b+".ser");
			ObjectInputStream in = new ObjectInputStream(filein);
	        DataUser user = (DataUser)in.readObject();
	        in.close();
	        
	        long setor = Long.parseLong(JOptionPane.showInputDialog("Masukan Jumlah Setor: "));
	        
	        if(setor>50000) {
	        	int opsi = JOptionPane.showOptionDialog(null, "Anda Akan melakukan setor tunai sebanyak Rp. "+ setor+"\nApakah Andi ingin melanjutkan transaksi tersebut? ", "Mohon Tunggu Sesaat", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	        	if(opsi==0) {
	        		saldoAkhir = saldo + setor;
	        		sisa = saldoAkhir;
			        DataUser user4 = new DataUser();
				    user4.nama= user.nama;
				    user4.saldo = String.valueOf(saldoAkhir);
				    FileOutputStream fileout;
				    
				    fileout = new FileOutputStream(String.valueOf(g+"\\"+b+".ser"));
					ObjectOutputStream out = new ObjectOutputStream(fileout);
					out.writeObject(user4);
					out.close();
					
					
	        	}else {
	        	}
	        }else {
        		JOptionPane.showMessageDialog(null, "Transaksi tidak bisa dilanjutkan\n"+"Minimal setor adalah Rp. 50.000,-");
        	}
		} catch (IOException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		 
		
	}
	public static void gantiNama(long saldo, String b, String namaSebelum, int batasTransaksi, int aa) {
		
		String namabaru = JOptionPane.showInputDialog("Masukan Nama Baru Anda");
		if(!(namabaru==null)) {
			DataUser user2 = new DataUser();
	        user2.nama= namabaru;
			user2.saldo = String.valueOf(saldo);
			FileOutputStream fileout;
			String[] options = new String[] {"Ya", "Tidak"};
	    	int op = JOptionPane.showOptionDialog(null, "Nama atas nama Rekening ini sebelumnya adalah: "+ namaSebelum+"\nAkan diganti dengan: "+namabaru+"\n Apakah anda ingin melanjutkan proses penggantian nama tersebut? ", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, 0);
	    	if (op==0) {
	    		try {
	    			fileout = new FileOutputStream(String.valueOf(g+"\\"+b+".ser"));
	    			ObjectOutputStream out = new ObjectOutputStream(fileout);
	    			out.writeObject(user2);
	    			out.close();
	    			JOptionPane.showMessageDialog(null, "Silahkan Login Kembali untuk penggantian Nama Baru Anda");
	            	batasTransaksi = 0;
	                aa=0;
	    		} catch (Exception e1) {
	    		}
			}else if(op==1) {
				batasTransaksi++;
				aa++;
			}  
		}
		 
	}
	public static void transaksi(String b, String namaAkun, String sandi) {
		try {
			FileInputStream filein = new FileInputStream(g+"\\"+b+".ser");
			ObjectInputStream in = new ObjectInputStream(filein);
	        DataUser user = (DataUser)in.readObject();
	        in.close();
	        int aa = 1;
	        long saldo= Long.parseLong(user.saldo);
	        System.out.println("saldosalso salso saldo" + user.saldo);
	        int batasTransaksi=1;
	        while (aa>0) {                     
	            while (batasTransaksi > 0) {
	                if (saldo>50000) {
	                	String[] options = new String[] {"1", "2", "3", "4", "5","6","7","Keluar"};
	                	int op = JOptionPane.showOptionDialog(null, "Selamat Datang " + user.nama + "\n Pilih Transaksi" + "\n1. Penarikan Tunai" + "\n2. Transfer "+"\n3. Cek Saldo"+"\n4. Cek No Rekening"+"\n5. Ganti Atas Nama Rekening Anda"+"\n6. Setor Tunai"+"\n7. Ganti Username/Sandi", "Message", JOptionPane.DEFAULT_OPTION, JOptionPane.PLAIN_MESSAGE,null, options, 0);
	                    if (op == 0) {
	                        long tarik = Long.parseLong(JOptionPane.showInputDialog("Masukan Nominal Tarik Tunai: "));     
	                        if (tarik<saldo && tarik>0) {
	                            sisa = saldo - tarik;
	                            JOptionPane.showMessageDialog(null, "Transaksi Berhasil\n" + "Sisa Saldo Anda sebanyak: Rp. " + sisa);
	                            saldo = sisa;	                        	
	                        }else {
	                            JOptionPane.showMessageDialog(null, "Transaksi tidak bisa dilanjukan.\n"+"Saldo tidak boleh kurang dari Rp.50.000,-\n" + "Sisa saldo sebanyak: Rp."+ saldo);                            
	                        }                                                
	                    }else if (op == 1) {
	                        try {
								transfer(saldo, sisa, batasTransaksi, aa);
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
	                        System.out.println(sisa);
	                        saldo = sisa;
	                    }else if (op == 2) {
	                        JOptionPane.showMessageDialog(null, "Sisa Saldo anda sebanyak: Rp. " +saldo);                        
	                    }else if(op == 3) {
	                    	JOptionPane.showMessageDialog(null, "Nomor Rekening Anda adalah:  " +b);
	                    }else if(op == 4) {
	                    	gantiNama(saldo, b, user.nama, batasTransaksi, aa);
	                    	if(batasTransaksi == 0 && aa==0) {
	                    		break;
	                    	}else {
	                    		batasTransaksi++;
	                    		aa++;
	                    	}
	                    	
	                    	batasTransaksi=0;
	                    	aa=0;
	                    	break;
	                    }else if(op==5) {
	                    	setorTunai(b, saldo);
	                    	System.out.println(sisa);
	                    	saldo = sisa;
	                    }else if(op==6) {
	                    	String[] pilih = {"Username", "Sandi", "Batal"};
	                    	int uu = JOptionPane.showOptionDialog(null, "Apa yang ingin Anda Ubah?"+"\n1. Username"+"\n2. Sandi", "Silahkan Pilih", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, pilih, 0);
	                    	if(uu == 0) {
	                    		String gag = JOptionPane.showInputDialog("Masukan Username Baru Anda") ;
	                    		int oh = JOptionPane.showOptionDialog(null, "Anda akan mengganti Username anda dengan "+gag+"\nYakin ingin melanjutkan proses penggantian Username", "Silahkan Pilh", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
	                    		if(oh == 0) {
	                    			gantiUsername(namaAkun, gag);
	                    			JOptionPane.showMessageDialog(null, "Silahkan Login Kembali ");
	                    			batasTransaksi=0;
	                    			aa=0;
	                    			break;
	                    		}else {
	                    			break;
	                    		}
	                    		
	                    	}else if(uu==1) {
	                    		String gag = JOptionPane.showInputDialog("Masukan Sandi Baru Anda: ") ;
	                    		int oh = JOptionPane.showOptionDialog(null, "Yakin ingin melanjutkan proses penggantian Sandi", "Silahkan Pilh", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, null, 0);
	                    		if(oh == 0) {
	                    			gantiSandi(sandi, gag);
	                    			JOptionPane.showMessageDialog(null, "Silahkan Login Kembali ");
	                    			batasTransaksi=0;
	                    			aa=0;
	                    			break;
	                    		}else {
	                    			break;
	                    		}
	                    	}else if(uu==2) {
	                    		break;
	                    	}
	                    }else if(op == 7) {
	                    	batasTransaksi = 0;
	                        aa=0;
	                        break;
	                    }
	                    else {
	                        JOptionPane.showMessageDialog(null, "Input Salah");
	                    }
	                    String[] gg = {"Ya", "Tidak"};
	                    int a2 = JOptionPane.showInternalOptionDialog(null, "Transaksi Anda telah selesai\n" + "Apakah Anda ingin melanjutkan Transaksi Lainnya?\n", "Mohon Tunggu", JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, gg, 0);        
	                    if (a2 == 0) {
	                        batasTransaksi++;
	                        aa++;                           
	                    }else if (a2 == 1 ) {
	                        batasTransaksi = 0;
	                        aa=0;                        
	                    }else {
	                        batasTransaksi = 0; 
	                        aa=0;
	                    }                   
	                }else {
	                    JOptionPane.showMessageDialog(null, "Saldo Anda tidak Mencukupi untuk melakukan transaksi\n"+"Sisa Saldo Anda adalah sebanyak:\nRp."+ saldo);
	                    int opsi = JOptionPane.showOptionDialog(null, "Apakah Anda ingin melakukan setor tunai? ", "Mohon Tunggu", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
	                    
	                    if(opsi==0) {
	                    	setorTunai(b,saldo);
	                    }if(opsi==1) {
	                    	batasTransaksi=0;
		                    aa=0;
		                    break;
	                    }
	                    saldo = sisa; 

	                }        
	            }    
	        }
	        JOptionPane.showMessageDialog(null, "Terima Kasih");
	        if(sisa == 0 || saldo == 0) {	
	        }else {
	        	DataUser user2 = new DataUser();
		        user2.nama= user.nama;
				user2.saldo = String.valueOf(sisa);			
				FileOutputStream fileout;
		        try {
					fileout = new FileOutputStream(String.valueOf(g+"\\"+b+".ser"));
					ObjectOutputStream out = new ObjectOutputStream(fileout);
					out.writeObject(user2);
					out.close();
				} catch (Exception e1) {
				}
	        } 
		} catch (FileNotFoundException e1) {
			//e1.printStackTrace();
			//JOptionPane.showMessageDialog(null, "Akun Tidak Terdaftar!");
		} catch(IOException e2) {
			//e2.printStackTrace();
		} catch(ClassNotFoundException e2) {
			//e2.printStackTrace();
		} catch(NullPointerException e2) {
			JOptionPane.showMessageDialog(null, "Terima Kasih");
		}catch(NumberFormatException e2) {
			JOptionPane.showMessageDialog(null, "Ada Kesalahan, Silahkan Ulangi Kembali");
		}
		forrek = null;
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
			// TODO: handle exception
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
			raf.close();
		} catch (Exception e) {
		}
	}
	public static void checkData(String usr, String sandi) {
		RandomAccessFile raf;
		try {
			raf = new RandomAccessFile(f+"\\login.txt", "rw");
			String line = raf.readLine();
			nama = line.substring(9);
			pass = raf.readLine().substring(9);
			ser = raf.readLine().substring(9);
			if(usr.equals(nama) & sandi.equals(pass)) {
				System.out.println("yes");
			}else {
				System.out.println("No");
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
	public static void logic(String usr, String sandi) {
		System.out.println(ln);
		try {
			RandomAccessFile raf = new RandomAccessFile(f+"\\login.txt", "rw");
			int c = 2;
			for(int i = 0; i<ln;i+=4) {
				c++;
				String foruser = raf.readLine().substring(9);
				String forpass = raf.readLine().substring(9);
				
				if(usr.equals(foruser)&& sandi.equals(forpass)) {
					forrek = raf.readLine().substring(9);
					System.out.println("correct!!\n"+"your number is: "+forrek);
					//new Transaksi(forrek, usr, sandi);
					break;
				}else if(i==ln-c) {
					//System.out.println("no");
					JOptionPane.showMessageDialog(null, "Akun tidak terdaftar");
					break;
				}
				for(int k=1; k<=3; k++) {
					System.out.println(k);
					raf.readLine();
					
				}
				
			}
		} catch (Exception e) {
			
			//JOptionPane.showMessageDialog(null, "Akun tidak terdaftar");
		}
	}
	public static void mainReplace(String a, String b) {
		Map<String, String> variabelMap = fillMap(a, b);
		Path path = Paths.get(f+"\\login.txt");
		Stream<String> lines;
		
		try {
			lines = Files.lines(path, Charset.forName("UTF-8"));
			List<String> replacedLines = lines.map(line -> replaceTag(line, variabelMap)).collect(Collectors.toList());
			Files.write(path, replacedLines, Charset.forName("UTF-8"));
			lines.close();
			System.out.println("Done");
		} catch (Exception e) {
			// TODO: handle exception
		}
	}	
	public static Map<String,String> fillMap(String a, String b){
		Map<String,String> map = new HashMap<String, String>();
		map.put(a,b);
		return map;
	}
	public static String replaceTag(String str, Map<String,String>map) {
		
		for(Map.Entry<String, String> entry : map.entrySet()) {
			if(str.contains(entry.getKey())) {
				str = str.replace(entry.getKey(), entry.getValue());
			}
		}
		
		
		return str;
		
	}

	public static void gantiUsername(String username, String usernamebaru){
		mainReplace(username, usernamebaru);
	}
	
	public static void gantiSandi(String sandi, String sandiBaru) {
		mainReplace(sandi, sandiBaru);
	}
	
	
	
}