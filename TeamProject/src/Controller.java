import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class Controller implements ActionListener{
	int reservationViewchangeCheck = 0;
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		Object obj = e.getSource();
		if(obj == MainView.findButton) {
			setTable();
		}
		else if(obj == MainView.reservationButton) {
			try {
				
				getTableResult();
				
				if(MainView.buildingResult == null) {
					JOptionPane.showMessageDialog(null, "올바른 값을 선택해 주십시오!");
				}
				else {
					setUserReservationData();
					if(reservationViewchangeCheck == 0) {	
						ReservationView reservationView = new ReservationView();
						MainView.frame.setVisible(false);
						reservationViewchangeCheck++;
					}
					else {
						ReservationView.frame.setVisible(true);
						ReservationView.textAreaSetting();
						ReservationView.frame.invalidate();
						ReservationView.frame.validate();
						ReservationView.frame.repaint();
						MainView.frame.setVisible(false);
						
					}
					
					
				}
				
			}catch (ArrayIndexOutOfBoundsException exception) {
				// TODO: handle exception
				JOptionPane.showMessageDialog(null, "예약하실 스터디룸을 선택해주세요!!");
			}
			
		}
		else if(obj == MainView.cancelButton) {
			
		}
		else if(obj == ReservationView.findButton) {
			
			ReservationView.frame.setVisible(false);
			MainView.frame.setVisible(true);
			
		}
		else if(obj == ReservationView.reservationButton) {
			
		}
		else if(obj == ReservationView.cancelButton) {
			
		}
	}
	// 예약정보를 예약View쪽의 데이터로 전송
	public void setUserReservationData() {
			
			ReservationView.reservationDate = (int) MainView.dateComboBox.getSelectedItem();
			ReservationView.reservationHour = (int) MainView.hourComboBox.getSelectedItem();
			ReservationView.reservationRoom = MainView.buildingResult + " " + MainView.roomResult;
			ReservationView.reservationPeople = MainView.peopleResult;
			ReservationView.reservationProjecter = MainView.projectCheckBox.isSelected();
			
	}
	// JTable에 입력되어있는 정보들을 변수에 저장
	public void getTableResult() {
			int getRow = MainView.table.getSelectedRow();
			MainView.buildingResult = (String) MainView.table.getValueAt(getRow, 0);
			MainView.roomResult = (String) MainView.table.getValueAt(getRow, 1);
			MainView.peopleResult = (String) MainView.table.getValueAt(getRow, 2);
		}
	// 테이블에 데이터를 셋팅
	public void setTable() {
		try {
			// 데이터 베이스에서 처리해야할 부분
			for(int i = 0; i < MainView.dataBaseRow; i++) {
				// 수용가능 인원 수  조회 (값 비교)
				// peopleResult = 데이터베이스에서 i번째 열 2번째 행(수용가능 인원수 부분)에서 가져오는 값 
				
				// TextField에 값과 데이터베이스에서 가져온 인원수 값인 peopleResult를 비교해서 TextField에 있는 값보다 같거나 큰 인원 수에 해당하는 정보들을 가져옴
				if(Integer.parseInt(MainView.peopleField.getText()) <= Integer.parseInt(MainView.peopleResult)) {
					MainView.mod.setValueAt(MainView.peopleResult, i, 2);
					// roomResult = 데이터베이스에서 i번째 열 1번째 행(스터디룸 부분)에서 가져오는 값
					MainView.mod.setValueAt(MainView.roomResult, i, 1); 
					// buildingResult = 데이터베이스에서 i번째 열 0번째 행(건물부분)에서 가져오는 값
					MainView.mod.setValueAt(MainView.buildingResult, i, 0);
				}
				else {
					clearTable(i);
				}
			}
			
			
		}catch (Exception e) {
			// TODO: handle exception
			JOptionPane.showMessageDialog(null, "인원 수를 정확히 입력해주세요!!");
		}
		
	}
	// 테이블에 있는 데이터를 공백으로 초기화
	public void clearTable(int i) {
		MainView.mod.setValueAt(" ", i, 0);
		MainView.mod.setValueAt(" ", i, 1);
		MainView.mod.setValueAt(" ", i, 2);
	}
}
