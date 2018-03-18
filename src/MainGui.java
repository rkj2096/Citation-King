//clear testing done !!!
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGui extends JFrame{
      JTextField url,jtt,jts,jtd,ath;
	  JLabel jlu,jlt,jls,jlp,jld,jla;
	  JButton jbp,jbc,jbe;
	  JTextArea jta;
	  GridBagLayout gb;
	  GridBagConstraints gc;
	  JPanel jp,jp1,jp2,jp3,jp4;
	  
	 public MainGui(){
        super("Self Citation Counter");
		
		//layout
		gb = new GridBagLayout();
		setLayout(gb);
		gc = new GridBagConstraints();
		
		//url , jtt & jts tf
		url = new JTextField("",50);
		url.setFont(new Font("Serif",Font.PLAIN,16));
		jtd = new JTextField("",5);
		jtd.setFont(new Font("Serif",Font.PLAIN,16));
		jtt = new JTextField("",5);
		jtt.setFont(new Font("Serif",Font.PLAIN,18));
		jtt.setEditable(false);
		jts = new JTextField("",5);
		jts.setFont(new Font("Serif",Font.PLAIN,18));
		jts.setEditable(false);
		ath = new JTextField("",15);
		ath.setFont(new Font("Serif",Font.PLAIN,18));
		ath.setEditable(false);
		
		//labels
		jlu =new JLabel("Enter url here:");
		jlt =new JLabel("Total citations:");
		jls =new JLabel("Self citations:");
		jlp =new JLabel("Paper-wise Citations:");
		jld =new JLabel("Depth:");
		jla =new JLabel("Author:");
		
		//buttons
		jbp =new JButton("Process");
		jbc =new JButton("Clear");
		jbe =new JButton("Exit");
		
		//paper wise citations ta box
		Box box=Box.createHorizontalBox();
		jta =new JTextArea(100,150);
		jta.setText("---------------------------------------------Paper---------------------------------------------------------------Citations---Self-citations\n");
		jta.setFont(new Font("Serif",Font.PLAIN,16));
        jta.setEditable(false);
		box.add(new JScrollPane(jta));
		
		//bottom buttons jbp jbc & jbe
		jp = new JPanel();
		jp.setLayout(new FlowLayout());
		jp.add(jbp);
		jp.add(jbc);
		jp.add(jbe);
		
		//panel 3
		jp2 = new JPanel();
		jp2.setLayout(new GridLayout(1,3));
		jp2.add(jld);
		jp2.add(jtd);
		jp2.add(jla);
		
		//panel 4
		jp3 = new JPanel();
		jp3.setLayout(new GridLayout(1,2));
		jp3.add(jls);
		jp3.add(jts);
		
		//panel 5
		jp4 = new JPanel();
		jp4.setLayout(new GridLayout(1,2));
		jp4.add(jlt);
		jp4.add(jtt);
		
		//panel 2
		jp1 = new JPanel();
		jp1.setLayout(new GridLayout(1,4));
	    jp1.add(jp2);
		jp1.add(ath);
		jp1.add(jp4);
		jp1.add(jp3);
		
		
		//add componenets
		gc.fill=GridBagConstraints.HORIZONTAL;
		addComponent(jlu,0,0,1,1);
		addComponent(url,1,0,1,1);
		addComponent(jp1,2,0,1,1);
		addComponent(jlp,3,0,1,1);
		addComponent(jp,5,0,1,1);
		gc.fill=GridBagConstraints.BOTH;
   	    gc.weightx = 1;
   	    gc.weighty = 1;
		addComponent(box,4,0,1,1);
		
		//action 
		Handler h = new Handler();
		jbp.addActionListener(h);
		jbc.addActionListener(h);
		jbe.addActionListener(h);
		
	}
	 private void addComponent(Component co,int r,int c,int w,int h)
   	    {
    		gc.gridx=c;
    		gc.gridy=r;
    		gc.gridwidth=w;
    		gc.gridheight=h;
    		gb.setConstraints(co,gc);
    		add(co);
   	    }
		
	private class Handler implements ActionListener{
		
		public void actionPerformed(ActionEvent e){
			if(e.getSource()==jbp)
			{
				//action
				try
				{
				Cite c = new Cite(url.getText().trim(),Integer.parseInt(jtd.getText().trim()));
				String a[] = c.info();
				jtt.setText(a[0]);
				jts.setText(a[1]);
				jta.setText(jta.getText()+a[2]);
				ath.setText(c.author());
				}
				catch(java.io.IOException io)
				{
					JOptionPane.showMessageDialog(null,"Connection Error!","Self Citation",JOptionPane.ERROR_MESSAGE);
				}
				catch(Exception x)
				{
					JOptionPane.showMessageDialog(null,"Wrong input!","Self Citation",JOptionPane.ERROR_MESSAGE);
				}
				
			}
			if(e.getSource()==jbc)
			{
				//clear
				url.setText("");
				jtt.setText("");
				jta.setText("");
				jts.setText("");
				jtd.setText("");
			}
			if(e.getSource()==jbe)
			{
				//exit
				System.exit(0);
			}
		}
	}	
	 public static void main(String[] args) {
        MainGui cd=new MainGui();
        cd.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        cd.setSize(870,500);
        cd.setVisible(true);
        cd.setResizable(true);
    }
	 
}