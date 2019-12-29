import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.Calendar;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


public class MainView extends JFrame{
	
	static JFrame frame = new JFrame();
	
	DecimalFormat df = new DecimalFormat("00");
    Calendar currentCalendar = Calendar.getInstance();

    int lastDay = Integer.parseInt(df.format(currentCalendar.getActualMaximum(Calendar.DAY_OF_MONTH ))); // 이번달의 마지막 날을 가져옴
    int currentDate = currentCalendar.get(currentCalendar.DATE); // 현재 날짜를 가져옴
   
    
	Controller buttonListener = new Controller();
	
	// 데이터 베이스 관련 변수들
	static String buildingResult = "데이터 베이스 건물 결과 값";
	static String roomResult = "데이터 베이스 스터디룸 결과 값";
	static String peopleResult = "20";
	
	static int dataBaseRow = 3;
	static int dataBaseCol = 3;
	
	// 첫번째 패널
	JLabel titleLabel = new JLabel("통합 스터디룸 예약 시스템");
	JPanel titlePanel = new JPanel();
	
	// 첫번째 패널 안의 첫번째 패널
	JPanel subPanel = new JPanel();
	
	// 첫번째 패널 안의 두번째 패널
	static JComboBox dateComboBox = new JComboBox();
	JLabel dateLabel = new JLabel("일");
	
	static JComboBox hourComboBox = new JComboBox();
	JLabel hourLabel = new JLabel("시");
	
	static JTextField peopleField = new JTextField(3);
	JLabel peopleLabel = new JLabel("명");
	
	static JCheckBox projectCheckBox = new JCheckBox();
	JLabel projectLabel = new JLabel("빔 프로젝터 유무");
	
	JPanel selectionPanel = new JPanel();
	
	// 세번째 패널
	static String[] header = {"건물명","스터디 룸 명", "사용가능 인원 수"};
	static String[][] data = new String[20][2];
	static DefaultTableModel mod = new DefaultTableModel(data, header) {
		public boolean isCellEditable(int rowIndex, int mColIndex) {
            return false;
        }
    };
	static JTable table = new JTable(mod);
	JScrollPane tablePanel = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	
	// 네번째 패널
	static JButton findButton = new JButton("조회");
	static JButton reservationButton = new JButton("예약");
	static JButton cancelButton = new JButton("취소");
	JPanel buttonPanel = new JPanel();
	
	public MainView() {
		setFrame();
		startUI();
	}
	
	public void setFrame() {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("통합 스터디룸 예약 시스템");
		frame.setSize(1000,700);
		frame.setVisible(true);
		
	}
	
	public void startUI() {
		
		titlePanel.setLayout(new BoxLayout(titlePanel ,BoxLayout.Y_AXIS));
		titleLabel.setFont(new Font("돋움", Font.PLAIN, 30));
		
		subPanel.setBorder(BorderFactory.createEmptyBorder(10,10,0,10));
		subPanel.add(titleLabel);
		
		titlePanel.add(subPanel);
		
		dateComboBox.setPreferredSize(new Dimension(100,30));
		hourComboBox.setPreferredSize(new Dimension(100,30));
		comboBoxSetting();
		
		selectionPanelSetting();
		titlePanel.add(selectionPanel);
		frame.repaint();
		
		tableSetting();
		
		buttonPanelSetting();
		
		
		findButton.addActionListener(buttonListener);
		reservationButton.addActionListener(buttonListener);
		cancelButton.addActionListener(buttonListener);
		
		addAllPanel();
		frame.invalidate();
		frame.validate();
		frame.repaint();
	}
	public void comboBoxSetting() {
		for(int i = currentDate; i <= lastDay; i++) {
			dateComboBox.addItem(i); // 현재날짜부터 마지막 날짜까지 dateComboBox에 Item추가
		}
		for(int i = 0; i <= 24; i++) {
			hourComboBox.addItem(i);
		}
	}
	
	public void selectionPanelSetting() {
		selectionPanel.add(dateComboBox);
		selectionPanel.add(dateLabel);
		selectionPanel.add(hourComboBox);
		selectionPanel.add(hourLabel);
		selectionPanel.add(peopleField);
		selectionPanel.add(peopleLabel);
		selectionPanel.add(projectCheckBox);
		selectionPanel.add(projectLabel);
		selectionPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		
	}
	public void tableSetting() {
		table.setRowHeight(25);

		tablePanel.setBorder(BorderFactory.createEmptyBorder(0, 30, 0, 30));
		tablePanel.setPreferredSize(new Dimension(1000,400));
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); // 테이블에 한 행만 선택되게 설정
		table.getTableHeader().setReorderingAllowed(false); // 테이블 헤더 이동불가
		table.getTableHeader().setResizingAllowed(false); // 테이블 헤더 크기 조정 불가
		
		
	}
	
	public void buttonPanelSetting() {
		buttonPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
		buttonPanel.add(findButton);		
		buttonPanel.add(reservationButton);		
		buttonPanel.add(cancelButton);
		
	}
	
	public void addAllPanel() {
		frame.add(titlePanel, BorderLayout.NORTH);
		frame.add(tablePanel,BorderLayout.CENTER);
		frame.add(buttonPanel, BorderLayout.SOUTH);
		
	}
	public static void main(String[] args) {
		 
		MainView main = new MainView();
		   
	}

	

}
