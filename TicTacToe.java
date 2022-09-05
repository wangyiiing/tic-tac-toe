package tictaktoc;
import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import javax.swing.border.LineBorder;

public class TicTacToe extends JFrame implements ActionListener
{
	//who2who = 'D': Joueur vs Joueur
	private char who2who;
	
	//whoFirst = 'W': ordinateur first		whoFirst = 'B': joueur first
	private char whoFirst = ' ';
	
	//Demandez si la création a changé
	private char isFirstChanged = 'n';
	
	//whoseTurn = 'W': 
	//whoseTurn = 'W': C'est au tour de l'ordinateur			whoseTurn = 'B': C'est au tour du joueur
	private char whoseTurn;
	
	//isGameOver = 'n': game begin	isGameOver = 'y': game over
	private char isGameOver = 'n';
	
	//Enregistrer les coordonnées x et y des mouvements
	private int[] chess_x = new int[9];
	private int[] chess_y = new int[9];
	//Enregistrer la position actuelle
	private int currentPosition = -1;
	
	//9 positions pour placer les pions
	private Cell cell[][] = new Cell[3][3];
		
	// Initialise la barre d'état
	private JLabel jlblStatus = new JLabel("Veuillez sélectionner un mode de combat dans 'Option (C)'");
	
	//Menu
	private JMenuItem jmiStart,jmiRestart,jmiRetract,jmiExit,jmiComputerFirst,jmiPlayerFirst,jmiPlayer2Player;
	
	//Initialisation de l'interface
    public TicTacToe()
    {
    	//Définir la barre de menus
    	JMenuBar jmb = new JMenuBar();
    	JMenu jmGame = new JMenu("Games(G)");
    	jmGame.setMnemonic('G');
    	jmb.add(jmGame);
    	
    	JMenu jmOption = new JMenu("Options(O)");
    	jmOption.setMnemonic('O');
    	jmb.add(jmOption);
    	
    	JMenu jmPlayer2Computer = new JMenu("Joueur vs Ordinateur");
    	jmPlayer2Computer.addSeparator();
    	
    	jmGame.add(jmiStart = new JMenuItem("Commencer",'S'));
    	jmGame.add(jmiRestart = new JMenuItem("Redémarrer",'R'));
    	jmGame.addSeparator();
    	jmGame.add(jmiRetract = new JMenuItem("Regret",'H'));
    	jmGame.addSeparator();
    	jmGame.add(jmiExit = new JMenuItem("Quitter",'E'));

    	jmOption.add(jmPlayer2Computer);
    	jmOption.addSeparator();
    	jmOption.add(jmiPlayer2Player = new JMenuItem("Joueur vs Joeur"));
    	
    	jmPlayer2Computer.add(jmiPlayerFirst = new JMenuItem("Le joueur fait le premier pas",'P'));
    	jmPlayer2Computer.add(jmiComputerFirst = new JMenuItem("L'ordinateur fait le premier pas",'A'));
    	
    	//Définir les touches de raccourci
    	jmiStart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,ActionEvent.CTRL_MASK));
    	jmiRestart.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R,ActionEvent.CTRL_MASK));
    	jmiRetract.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_H,ActionEvent.CTRL_MASK));
    	jmiExit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,ActionEvent.CTRL_MASK));
    	jmiPlayerFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P,ActionEvent.CTRL_MASK));
    	jmiComputerFirst.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C,ActionEvent.CTRL_MASK));
    	
    	setJMenuBar(jmb);
    	
    	
    	jmiStart.addActionListener(this);
    	jmiRestart.addActionListener(this);
    	jmiRetract.addActionListener(this);
    	jmiExit.addActionListener(this);
    	jmiPlayer2Player.addActionListener(this);
    	jmiPlayerFirst.addActionListener(this);
    	jmiComputerFirst.addActionListener(this);
    	
    	////Définir le panneau, ajouter 9 petits panneaux
    	JPanel p = new JPanel();
    	p.setLayout(new GridLayout(3,3,0,0));
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    			p.add(cell[i][j] = new Cell());
    			
    	p.setBorder(new LineBorder(Color.BLACK,1));
    	jlblStatus.setBorder(new LineBorder(Color.BLACK,1));
    	
    	this.getContentPane().add(p,BorderLayout.CENTER);
    	this.getContentPane().add(jlblStatus,BorderLayout.SOUTH);
    }
    
    //Définissez qui passe en premier
    public void setWhoFirst(char whoFirst)
    {
    	this.whoFirst = whoFirst;
    }
    
    //Choisissez un mode de jouer
    public void setWho2Who(char who2who)
    {
    	this.who2who = who2who;
    }
    
    public void setWhoseTurn(char whoseTurn) {
    	this.whoseTurn=whoseTurn;
    }
    
    public Cell getCell(int x,int y) {
    	return cell[x][y];
    }
    
    //Déterminer si un camp gagne
    public boolean isWon(char token)
    {
    	for(int i = 0; i < 3; i++)
    	{
    		if(cell[i][0].getToken() == token && cell[i][1].getToken() == token && cell[i][2].getToken() == token)
    			return true;
    		else if(cell[0][i].getToken() == token && cell[1][i].getToken() == token && cell[2][i].getToken() == token)
    			return true;
    		else if(cell[0][0].getToken() == token && cell[1][1].getToken() == token && cell[2][2].getToken() == token)
    			return true;
    		else if(cell[0][2].getToken() == token && cell[1][1].getToken() == token && cell[2][0].getToken() == token)
    			return true;
    	}
    	return false;
    }
    
    //Déterminer si la grille est plein
    public boolean isFull()
    {
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    			if(cell[i][j].getToken() == ' ')
    				return false;
    	return true;
    }
        
    //barre de menu réactive
    public void actionPerformed(ActionEvent e)
    {
    	//Commemcer
    	if(e.getSource() == jmiStart)
    	    start();
    	//Redémarrer
    	else if(e.getSource() == jmiRestart)
    		restart();
    	//Regret
    	else if(e.getSource() == jmiRetract)
    	{
    		//L'ordinateur joue en premier, vous pouvez regretter un coup et il n'y a qu'un seul coup sur la grille 
    		if(whoFirst == 'B' && isFirstChanged == 'y')
    		{
    			if(currentPosition >= 1 && isGameOver == 'n')
    			{
    				int x = chess_x[currentPosition];
    				int y = chess_y[currentPosition];  
    				System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
    				currentPosition = currentPosition - 1;
    				cell[x][y].setToken(' ');
    				cell[x][y].setIsChecked('n');
    				
    				//****************Deux regrets consécutifs*****************
        			x = chess_x[currentPosition];
        			y = chess_y[currentPosition];  
        			System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
        			currentPosition = currentPosition - 1;
        			cell[x][y].setToken(' ');
        			cell[x][y].setIsChecked('n');
        			//****************************************
        			
    				if(whoseTurn == 'W')
    				{
    					whoseTurn = 'B';
    					jlblStatus.setText("C'est au tour du joueur......");
    				}
    				else if(whoseTurn == 'B')
    				{
    					whoseTurn = 'W';
    					jlblStatus.setText("C'est au tour de l'ordinateur...");
    				}
    			}
    		}
    		
    		//Le joueur se déplace en premier et peut se repentir jusqu'à ce que le plateau soit vide 
    		else if(whoFirst == 'B' && isFirstChanged == 'n' && who2who != 'D')
    		{
    			if(currentPosition >= 0 && isGameOver == 'n')
        		{
        			int x = chess_x[currentPosition];
        			int y = chess_y[currentPosition];  
        			System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
        			currentPosition = currentPosition - 1;
        			cell[x][y].setToken(' ');
        			cell[x][y].setIsChecked('n');
        			
        			
        			//****************Deux regrets consécutifs*****************
        			x = chess_x[currentPosition];
        			y = chess_y[currentPosition];  
        			System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
        			currentPosition = currentPosition - 1;
        			cell[x][y].setToken(' ');
        			cell[x][y].setIsChecked('n');
        			//****************************************
        			
        			if(whoseTurn == 'W')
        			{
        				whoseTurn = 'B';
        				jlblStatus.setText("C'est au tour du joueur......");
        			}
        			else if(whoseTurn == 'B')
        			{
        				whoseTurn = 'W';
        				jlblStatus.setText("C'est au tour de l'ordinateur...");
        			}
        		}
    		}
    		
    		//Joueur vs Joueur --> Regret
    		else if(who2who == 'D')
    		{
    			if(currentPosition >= 0 && isGameOver == 'n')
    			{
    				int x = chess_x[currentPosition];
    				int y = chess_y[currentPosition];  
    				System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
    				currentPosition = currentPosition - 1;
    				cell[x][y].setToken(' ');
    				cell[x][y].setIsChecked('n');
    				
    				if(whoseTurn == 'W')
    				{
    					whoseTurn = 'B';
    					jlblStatus.setText("C’est au tour de 'X'...");
    				}
    				else if(whoseTurn == 'B')
    				{
    					whoseTurn = 'W';
    					jlblStatus.setText("C’est au tour de 'O'...");
    				}
    			}
    		}
    	}
    	//Quitter
    	else if(e.getSource() == jmiExit)
    		System.exit(0);
    	//Joueur vs Joueur
    	else if(e.getSource() == jmiPlayer2Player)
    	{
    		setWho2Who('D');
    		whoseTurn = 'B';
    		jlblStatus.setText("'Joueur vs Joueur -> Cliquez sur 'Commencer' dans 'Game'");
    	}
    	//Joueur first
    	else if(e.getSource() == jmiPlayerFirst)
    	{
    		setWhoFirst('B');
    		jlblStatus.setText("''Joueur vs ordinateur -> Joueur first -> Cliquez sur 'Commencer' dans 'Games'");
    	}
    	//Ordinateur first
    	else if(e.getSource() == jmiComputerFirst)
    	{
    		setWhoFirst('W');
    		jlblStatus.setText("'Joueur vs ordinateur -> Ordinateur first -> Cliquez sur 'Commencer' dans 'Games'");
    	}
    }

    //variale 
    public void start()
    {
    	if(whoFirst == 'B')
    		whoseTurn = 'B';
    	else if(whoFirst == 'W')
    	{
    		whoseTurn = 'W';
    		cell[1][1].setToken('W');
    		Retract();
    		jlblStatus.setText("C'est au tour du joueur...");
    	}
    	else
    	{
    		whoFirst = 'B';
    	}
    }
    
    //Redémarrer - Initialisation de l'interface
    public void restart()
    {
    	setWho2Who(' ');
    	setWhoFirst(' ');
    	whoseTurn = ' ';
    	currentPosition = -1;
    	whoFirst = ' '; 
    	isFirstChanged = 'n';
    	isGameOver = 'n';
    	
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    		{
    			cell[i][j].setToken(' ');
    			cell[i][j].setIsChecked('n');
    		}
    	
    	for(int k = 0; k < 9; k++)
    	{
    		chess_x[k] = 0;
    		chess_y[k] = 0;
    	}
    	jlblStatus.setText("Veuillez sélectionner un mode de combat dans 'Option (O)'");
    	
    }
    
    //Enregistrez les positions des pions, utilisé pour les regrets
    public void Retract()
    {
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    		{
    			if(cell[i][j].getIsChecked() == 'n' && cell[i][j].getToken() != ' ')
    			{
    				currentPosition++;
    				chess_x[currentPosition] = i;
    				chess_y[currentPosition] = j;
    				System.out.println(currentPosition + ":" + chess_x[currentPosition] + chess_y[currentPosition]);
    				cell[i][j].setIsChecked('y'); 				
    			}
    			
    		}
    }
    
    //définir la fonction
    //O -> Ordinateur；X -> joueur
    public int Evaluate_BW_Delta()
    {
    	int[] BlackNum = new int[8];
    	int[] WhiteNum = new int[8];
    	int[][][] solution = new int[3][3][8];
    	int count = 0;
    	int BlackValue = 0;
    	int WhiteValue = 0;
    	
    	//Définissez chaque type de victoire (8 types au total) et initialisez-le
    	//3 Lignes
    	for(int i = 0; i < 3; i++)
    	{
    		for(int j = 0; j < 3; j++)
    		{
    			solution[i][j][count] = 1;
    		}
    		count++;
    	}
    	
    	//3 colones
    	for(int i = 0; i < 3; i++)
    	{
    		for(int j = 0; j < 3; j++)
    		{
    			solution[j][i][count] = 1;
    		}
    		count++;
    	}
    	
    	//Diagonales
    	for(int i = 0; i < 3; i++)
    	{
    		solution[i][i][count] = 1;
    	}
    	count++;
    	
    	
    	for(int i = 0; i < 3; i++)
    	{
    		solution[2-i][i][count] = 1;
    	}
    	count++;
    	
    	//déterminer la fonction
    	for(int k = 0; k < count; k++)
    	{
    		for(int i = 0; i < 3; i++)
    			for(int j = 0; j < 3; j++)
    			{
    				if(solution[i][j][k] == 1 && cell[i][j].getToken() == 'B')
    					BlackNum[k] = BlackNum[k] + 1;
    				if(solution[i][j][k] == 1 && cell[i][j].getToken() == 'W')
    					WhiteNum[k] = WhiteNum[k] + 1;
    			}
    		
    		//'X'
    		if(WhiteNum[k] == 0)
    		{
    			if(BlackNum[k] == 1)
    				BlackValue = BlackValue + 15;
    			else if(BlackNum[k] == 2)
    				BlackValue = BlackValue + 100;
    			else if(BlackNum[k] == 3)
    				BlackValue = BlackValue + 999;
    		}
    		//'O'
    		else if(BlackNum[k] == 0)
    		{
    			if(WhiteNum[k] == 1)
    				WhiteValue = WhiteValue + 10;
    			else if(WhiteNum[k] == 2)
    				WhiteValue = WhiteValue + 50;
    			else if(WhiteNum[k] == 3)
    				WhiteValue = WhiteValue + 999;
    		}
    	}
    	return WhiteValue - BlackValue;
    }
    
    //Max(alpha-beta pruning)
    public int Max(int depth,int x,int y,int alpha,int beta)
    {
    	int temp = 0;
    	
    	if(depth <= 0)
    	{
    		return Evaluate_BW_Delta();
    	}
    	
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    		{
    			if(cell[i][j].getToken() == ' ')
    			{
    				cell[x][y].setVirtualToken('W');
    				temp = Min(depth-1,i,j,alpha,beta);
    				if(alpha < temp)
    				{
    					alpha = temp;
    				}
    				if(alpha > beta)
    				{
    					return alpha;
    				}
    				cell[x][y].setVirtualToken(' ');
    			}
    		}
    	return beta;
    }
    
    //Min(alpha-beta pruning)
    public int Min(int depth,int x,int y,int alpha,int beta)
    {
    	int temp = 0;
    	
    	if(depth <= 0)
    	{
    		return Evaluate_BW_Delta();
    	}
    	
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    		{
    			if(cell[i][j].getToken() == ' ')
    			{
    				cell[x][y].setVirtualToken('B');
    				temp = Max(depth-1,i,j,alpha,beta);
    				if(temp < beta)
    				{
    					beta = temp;
    				}
    				if(beta < alpha)
    				{
    					return beta;
    				}
    				cell[x][y].setVirtualToken(' ');
    			}
    		}
    	return alpha;
    }
    
    
    //Calculer la position où l'ordinateur place les pions
    public void SetComputerPosition()
    {
    	int BW_delta = -1000;
    	int temp;
    	int x = 0;
    	int y = 0;
    	for(int i = 0; i < 3; i++)
    		for(int j = 0; j < 3; j++)
    		{
    			if(cell[i][j].getToken() == ' ')
    			{
    				cell[i][j].setVirtualToken('W');
    				temp = Max(0,i,j,-1000,1000);
    				System.out.println(i + "," + j + " : " +temp);
    				if(temp > BW_delta)
    				{
    					x = i;
    					y = j;
    					BW_delta = temp;
    				}
    				cell[i][j].setVirtualToken(' ');
    			}
    		}
    	cell[x][y].setToken('W');
    }
    
    //La classe interne Cell, car vous devez utiliser les variables et les méthodes de la classe TicTacToe
    public class Cell extends JPanel implements MouseListener
    {
    	private char token = ' ';
    	private char isChecked = 'n';
    	
    	public Cell()
    	{
    		setBackground(Color.gray);
    		setBorder(new LineBorder(Color.green ,1));
    		addMouseListener(this);
    	}
    	
    	public char getToken()
    	{
    		return token;
    	}
    	
    	public char getIsChecked()
    	{
    		return isChecked;
    	}
    	
    	public void setToken(char token)
    	{
    		this.token = token;
    		repaint();
    	}
    	
    	public void setIsChecked(char isChecked)
    	{
    		this.isChecked = isChecked;
    	}
    	
    	public void setVirtualToken(char token)
    	{
    		this.token = token;
    	}
    	
    	//joueur vs ordinateur 'O' (ordinateur) et 'X' (joueur) jouent à tour de rôle aux échecs
    	public void alternate()
    	{
    		if(token == ' ' && isGameOver == 'n')
			{
    			if(whoseTurn == 'B' )
    			{
    				setToken('B');
    				Retract();
    				whoseTurn = 'W';
    				jlblStatus.setText("C'est au tour de l'ordinateur...");
				
    				if(isWon('B') == true)
    				{
    					isGameOver = 'y';
    					setWhoFirst(' ');
    					jlblStatus.setText("Jeu terminé et Joueur gagne ! ...");
    					JOptionPane.showMessageDialog(this,"Le joueur gagne! Wow ! Vous êtes incroyable !","Notification",JOptionPane.INFORMATION_MESSAGE);
    				}
    				else if(isFull() == true)
    				{
    					isGameOver = 'y';
    					setWhoFirst(' ');
    					jlblStatus.setText("Jeu terminé et Match nul...");
    					JOptionPane.showMessageDialog(this,"Match nul！","Notification",JOptionPane.INFORMATION_MESSAGE);
    				}
				
    				if(isGameOver == 'n')
    				{
    					SetComputerPosition();
    					Retract();
    					whoseTurn = 'B';
    					jlblStatus.setText("C'est au tour du joueur...");

    					if(isWon('W') == true)
    					{
    						isGameOver = 'y';
    						setWhoFirst(' ');
    						jlblStatus.setText("Jeu terminé et Ordinateur gagne !...");
    						JOptionPane.showMessageDialog(this,"L'ordinateur gagne ! Continue d'essayer！","Notification",JOptionPane.INFORMATION_MESSAGE);
    					}
    					else if(isFull() == true)
    					{
    						isGameOver = 'y';
    						setWhoFirst(' ');
    						jlblStatus.setText("Jeu terminé et Match nul...");
    						JOptionPane.showMessageDialog(this,"Match nul！","Notification",JOptionPane.INFORMATION_MESSAGE);
    					}
    				}
    			}
			}
    	}
    	
    	//Joueur vs Joueur
    	public void D_alternate()
    	{
    		if(token == ' ' && isGameOver == 'n')
			{
    			if(whoseTurn == 'B')
    			{
    				setToken('B');
    				Retract();
    				whoseTurn = 'W';
    				jlblStatus.setText("C'est au tour de 'O'......");
				
    				if(isWon('B') == true)
    				{
    					isGameOver = 'y';
    					jlblStatus.setText("Jeu termine et 'X' gagne！");
    					JOptionPane.showMessageDialog(this," 'X' gagne！","Notification",JOptionPane.INFORMATION_MESSAGE);
    				}
    				else if(isFull() == true)
    				{
    					isGameOver = 'y';
    					jlblStatus.setText("Jeu terminé et Match nul...");
    					JOptionPane.showMessageDialog(this,"Match nul！","Notification",JOptionPane.INFORMATION_MESSAGE);
    				}
    			}
				else if(whoseTurn == 'W')
				{
					setToken('W');
					Retract();
					whoseTurn = 'B';
					jlblStatus.setText("C'est au tour de 'X'...");

					if(isWon('W') == true)
					{
						isGameOver = 'y';
						jlblStatus.setText("Jeu termine et 'O' gagne！...");
						JOptionPane.showMessageDialog(this,"'O' gagne！","Notification",JOptionPane.INFORMATION_MESSAGE);
					}
					else if(isFull() == true)
					{
						isGameOver = 'y';
						jlblStatus.setText("Jeu terminé et Match nul...");
						JOptionPane.showMessageDialog(this,"Match nul","Notification",JOptionPane.INFORMATION_MESSAGE);
					}
				}
			}
    	}
    	
    	//paintComponent
    	public void paintComponent(Graphics g)
    	{
    		super.paintComponent(g);
    		
    		if(token == 'B')
    		{
    			g.setColor(Color.BLACK);
    			Graphics2D g2 = (Graphics2D)g;  
    			g2.setStroke(new BasicStroke(3.0f));
    			g.drawLine(30, 30, getSize().width-30, getSize().height-30);
    			g.drawLine(getSize().width-30, 30, 30, getSize().height-30);
    		}
    		
    		else if(token == 'W')
    		{
    			Graphics2D g2 = (Graphics2D)g;  
    			g2.setStroke(new BasicStroke(5.0f));
    			g.setColor(Color.blue);
    			g.drawOval(28, 28, getSize().width - 56,getSize().height - 56);
    			g.setColor(Color.red);
    			g.fillOval(30,30,getSize().width - 60,getSize().height - 60);
    		}
    	}
    	
    	//Réponse de la souris
    	public void mouseClicked(MouseEvent e)
    	{
    		//Joueur vs Joueur
    		if(who2who == 'D' && whoFirst == 'B')
    		{
    			D_alternate();
    		}
    		//Joueur vs ordinateur
    		else
    		{
    			//ordinateur first
    			if(whoFirst == 'W' && whoseTurn == 'W')
    			{
    				whoseTurn = 'B';
    				alternate();
    				setWhoFirst('B');
    				isFirstChanged = 'y';
    			}
    		
    			//joueur first
    			else if(whoFirst == 'B' && whoseTurn == 'B')
    			{
    				alternate();
    			}
    		}
    	}
    	
    	public void mousePressed(MouseEvent e)
    	{
    		
    	}
    	
    	public void mouseReleased(MouseEvent e)
    	{
    		
    	}
    	
    	public void mouseEntered(MouseEvent e)
    	{
    		
    	}
    	
    	public void mouseExited(MouseEvent e)
    	{
    		
    	}
    }
}