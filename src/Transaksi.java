import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.*;

public class Transaksi  implements ActionListener, KeyListener{
	JPanel panel = new JPanel();
	JFrame frame = new JFrame();
	static File f = new File("data");
	static File g = new File("user");
	
	public  String YELLOW_PAGE = "yellow page";
    public  String RED_PAGE = "red page";
    private  CardLayout cLayout;
    private  JPanel mainPane;
    boolean isRedPaneVisible;
	
	//Untuk Transaksi
	static long saldo;
	static String nama;
	
	
	public static void main(String[] args) {
		new Transaksi("", "", "");
	}
	
	JButton tarik = new JButton ("Tarik Tunai");
	JButton transfer = new JButton("Transfer");
	JButton cekSaldo = new JButton("Cek Saldo ");
	JButton cekrek = new JButton("Cek Rekening");
	JButton gantiNama = new JButton("Ubah Nama");
	JButton setor = new JButton("Setor Tunai");
	JButton usersandi = new JButton("Ubah ID/ Sandi");
	JButton keluar = new JButton("EXIT");
	
	JButton[] numberButtons = new JButton[10];
	JButton del, ok;
	
	int tinggi = 900;
	int lebar = 500;
	int panjangteks = 300;
	int lebarteks = 30;
	Timer timer = new Timer();
	
	TimerTask task;	
	
	JTextArea teks;
	JTextArea teks2;
	
	Transaksi(String ser, String id, String pass) {
		
		Maintransaksi(ser);
		
		teks = new JTextArea();
		//teks.setBounds(50,0, 400, 350);
		teks.setEditable(false);
		teks.setBackground(Color.blue);
		teks.setLineWrap(true);
		teks.setWrapStyleWord(true);
		teks.setForeground(Color.white);
		teks.setFont(new Font("Monospaced", Font.BOLD, 30));
		
		teks2 = new JTextArea(" Welcome " + nama);
		//teks.setBounds(50,0, 400, 350);
		teks2.setEditable(false);
		teks2.setBackground(Color.blue);
		teks2.setLineWrap(true);
		teks2.setBounds(50,0, 400, 50);
		teks2.setForeground(Color.white);
		teks2.setFont(new Font("Monospaced", Font.BOLD, 20));
		
		JScrollPane sc = new JScrollPane(teks);
		sc.setBounds(50,50, 400, 300);
		
		
		
		tarik.setBounds(sc.getX()+sc.getWidth()-panjangteks/2, sc.getY()+sc.getHeight()+10, panjangteks/2, lebarteks*2);
		tarik.setBackground(new Color(91, 198, 255));
		tarik.setFocusable(false);
		
		
		transfer.setBounds(tarik.getX(), tarik.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		transfer.setBackground(new Color(91, 198, 255));
		transfer.setFocusable(false);
		transfer.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				//TODO Auto-generated method stub
				teks.setText("");
				
			}
			
		});
		
		cekSaldo.setBounds(tarik.getX(), transfer.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		cekSaldo.setBackground(new Color(91, 198, 255));
		cekSaldo.setFocusable(false);
		cekSaldo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				teks2.setText("Mohon Tunggu sesaat");
				task = new TimerTask() {
					@Override
					public void run() {
						
						teks.setText("Saldo anda sebanyak Rp. "+saldo);
						teks2.setText("");	
						
						
					}
				};
				timer.schedule(task,1000);
			}
			
		});
		
		cekrek.setBounds(tarik.getX(), cekSaldo.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		cekrek.setBackground(new Color(91, 198, 255));
		cekrek.setFocusable(false);
		cekrek.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				teks.setText("");
			}
			
		});
		
		gantiNama.setBounds(tarik.getX(), cekrek.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		gantiNama.setBackground(new Color(91, 198, 255));
		gantiNama.setFocusable(false);
		gantiNama.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				teks.setText("");
			}
			
		});
		
		setor.setBounds(gantiNama.getX(), gantiNama.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		setor.setBackground(new Color(91, 198, 255));
		setor.setFocusable(false);
		setor.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		usersandi.setBounds(setor.getX(), setor.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		usersandi.setBackground(new Color(91, 198, 255));
		usersandi.setFocusable(false);
		usersandi.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		keluar.setBounds(usersandi.getX(), usersandi.getY()+lebarteks*2+10, panjangteks/2, lebarteks*2);
		keluar.setBackground(new Color(91, 198, 255));
		keluar.setFocusable(false);
		keluar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
		JPanel panel2 = new JPanel();
		panel2.setBounds(sc.getX(),sc.getY()+sc.getHeight()+10,sc.getWidth()-panjangteks/2, tinggi/2);
		panel2.setLayout(new GridLayout(3,3));
		panel2.setBackground(new Color(91, 198, 255));
		
		del = new JButton("Del");
		ok = new JButton("Ok");
		
		
		
		for (int i=0; i<10; i++) {
			numberButtons[i] = new 	JButton(String.valueOf(i));
			numberButtons[i].setFocusable(false);
			numberButtons[i].addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent e) {
					// TODO Auto-generated method stub
					int i;
					for(i=0; i<10; i++) {
						
						if(e.getSource()==numberButtons[i]) {
							teks.setText(teks.getText().concat(String.valueOf(i)));
						  }
					}
				}
				
			});
		}
		
		panel2.add(numberButtons[1]);
		panel2.add(numberButtons[2]);	
		panel2.add(numberButtons[3]);
		panel2.add(ok);
		panel2.add(numberButtons[4]);
		panel2.add(numberButtons[5]);
		panel2.add(numberButtons[6]);
		panel2.add(del);
		panel2.add(numberButtons[7]);
		panel2.add(numberButtons[8]);
		panel2.add(numberButtons[9]);
		panel2.add(numberButtons[0]);
		
		
		
				
		
		panel.setPreferredSize(new Dimension(lebar, tinggi));
		panel.setBackground(new Color(91, 198, 255));
		panel.setLayout(null);
		panel.add(transfer);
		panel.add(tarik);
		panel.add(cekSaldo);
		panel.add(cekrek);
		panel.add(gantiNama);
		panel.add(setor);
		panel.add(usersandi);
		panel.add(keluar);
		panel.add(teks2);
		panel.add(sc);
		panel.add(panel2);
		
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.add(panel);
		frame.pack();
		frame.setLayout(null);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		
		
		tarik.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				int a =1;
				while(a>0) {
					teks2.setText("Masukan Jumlah Tarik Tunai: "+"\n");
					teks.setText("");
					String aa = teks.getText();
					System.out.println(aa);
					
					
				}
				
			}
			
		});
	}
	void switchPanes() {
        if (isRedPaneVisible) {
        	showYelloPane();
        }
        else { 
        	showRedPane();
        }
    }

    void showRedPane() {
        cLayout.show(mainPane, RED_PAGE);
        isRedPaneVisible = true;
    }

    void showYelloPane() {
        cLayout.show(mainPane, YELLOW_PAGE);
        isRedPaneVisible = false;
    }
	
	public void Maintransaksi(String ser) {
		try {
			FileInputStream filein = new FileInputStream(g+"\\"+ser+".ser");
			ObjectInputStream in = new ObjectInputStream(filein);
	        DataUser user = (DataUser)in.readObject();
	        in.close();
	        
	        saldo = Long.parseLong(user.saldo);
	        nama  = user.nama;
	        
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "Akun Tidak Terdaftar");
			frame.setVisible(false);
		}
		
		
	}
	
	
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

}
