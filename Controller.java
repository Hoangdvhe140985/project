/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import GUI.Frame;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

/**
 *
 * @author Admin
 */
public class Controller {

    private Frame f = new Frame();
    private BigDecimal firstNum, secondNum;
    private int operator = -1;
    //người dùng có thể nhập số mới
    private boolean operatorProcess = false;
    //khi input 1 số -> nhập operator(+-*/) ->  process = true
    private boolean resetNumber = false;//liên quan to memory when click mr mc m+ m-
    //remove number have saved into memory
    private BigDecimal memory = new BigDecimal("0");

    public Controller() {
        f.setResizable(false);//set hui to not change size
        f.setLocationRelativeTo(null);
        f.setVisible(true);
        // tạo hàm gọi event for mọi nút
        btn0();
        btn1();
        btn2();
        btn3();
        btn4();
        btn5();
        btn6();
        btn7();
        btn8();
        btn9();
        btnAdd();
        btnDivide();
        btnDot();
        btnInvert();
        btnMAdd();
        btnMC();
        btnMR();
        btnMSub();
        btnMultiply();
        btnNegate();
        btnPercent();
        btnResult();
        btnSqrt();
        btnSubtract();
        lblClear();
        //đặt số đầu là 0
        f.getTxtDisplay().setText("0");
    }

    //lấy giá trị từ text on display
    public BigDecimal getValue() {
        //lấy giá trị trừ màn hình
        String value = f.getTxtDisplay().getText();
        BigDecimal temp = new BigDecimal(value);
        return temp;
    }
   
    // nhấn nút cho all number
    public void pressNumber(int number) {
        //người dùng đã nhấn 1 trong 4 operators
        if (operatorProcess == true || resetNumber == true) {
            f.getTxtDisplay().setText("");// đẻ nhấm 1 số mới
            operatorProcess = false;//để calculate
            resetNumber = false;//user có thể nhấn 1 new number
        }
        BigDecimal temp = new BigDecimal(f.getTxtDisplay().getText() + number);
        f.getTxtDisplay().setText(temp + "");
    }

     //tạo event cho mọi nút number
    public void btn0() {
        f.getBtn0().addActionListener((e) -> {
            pressNumber(0);
        });
    }

    public void btn1() {
        f.getBtn1().addActionListener((e) -> {
            pressNumber(1);
        });
    }

    public void btn2() {
        f.getBnt2().addActionListener((e) -> {
            pressNumber(2);
        });
    }

    public void btn3() {
        f.getBtn3().addActionListener((e) -> {
            pressNumber(3);
        });
    }

    public void btn4() {
        f.getBtn4().addActionListener((e) -> {
            pressNumber(4);
        });
    }

    public void btn5() {
        f.getBnt5().addActionListener((e) -> {
            pressNumber(5);
        });
    }

    public void btn6() {
        f.getBtn6().addActionListener((e) -> {
            pressNumber(6);
        });
    }

    public void btn7() {
        f.getBtn7().addActionListener((e) -> {
            pressNumber(7);
        });
    }

    public void btn8() {
        f.getBtn8().addActionListener((e) -> {
            pressNumber(8);
        });
    }

    public void btn9() {
        f.getBtn9().addActionListener((e) -> {
            pressNumber(9);
        });
    }

    //nhấm nút dot
    public void pressDot() {
        if (operatorProcess == true) {
            //nguoif dùng đã chọn 1 trong 4 operator
            f.getTxtDisplay().setText("0");//để nhấn 1 số mới
            operatorProcess = false;//để tính toán
        }
        // nếu . không tồn tại ->đặt chấm bên phải number on the màn hình
        //nếu . tồn tại -> không làm gì
        if (!f.getTxtDisplay().getText().contains(".")) {
            f.getTxtDisplay().setText(f.getTxtDisplay().getText() + ".");
        }
    }

    //tạo event cho nút .
    public void btnDot() {
        f.getBtnDot().addActionListener((e) -> {
            pressDot();
        });
    }

    // nhấm clear 
    public void Clear() {
        f.getTxtDisplay().setText("0");
        firstNum = new BigDecimal("0");
        secondNum = new BigDecimal("0");
        memory = new BigDecimal("0");
        operator = -1;
        operatorProcess = false;
        resetNumber = false;
        f.getBtnMR().setBackground(new Color(255, 233, 148));//set color of btn mR
        f.getBtnMR().setEnabled(false);//tắt nút mr
    }

    private void lblClear() {
        f.getLblClear().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                Clear();
            }
        });
    }

    //hàm tính toán
    public void calculate() {
        if (f.getTxtDisplay().getText().equals("ERROR")) {
            f.getTxtDisplay().setText("0");
            firstNum = new BigDecimal("0");
            secondNum = new BigDecimal("0");
        }
        try {
            if (operatorProcess == false) {
                if (operator == -1) {//k nhấn operator -> firstNum
                    firstNum = getValue();
                } else {// nhấn operator -> secondNum
                    secondNum = getValue();
                }//nếu phép chia mà số thứ 2  = 0 -> lỗi
                if (operator == 4 && secondNum.doubleValue() == 0) {
                    throw new Exception();
                } else {
                    switch (operator) {
                        case 1:
                            MathContext mc = new MathContext(17);//lấy 17 số sau .
                            firstNum = firstNum.add(secondNum, mc);
                            break;
                        case 2:
                            mc = new MathContext(17);
                            firstNum = firstNum.subtract(secondNum, mc);
                            break;
                        case 3:
                            mc = new MathContext(17);
                            firstNum = firstNum.multiply(secondNum, mc);
                            break;
                        case 4:
                            mc = new MathContext(17);
                            firstNum = firstNum.divide(secondNum, mc);
                            break;
                    }
                }
            }
            //sẽ đặt kết quả ra màn hình khi chúng ta nhấp vào nút toán tử thứ hai hoặc nút kết quả
            BigDecimal temp = firstNum;
            temp = temp.round(new MathContext(16, RoundingMode.HALF_UP));
            operatorProcess = true;//để nhấm 1 new number
            f.getTxtDisplay().setText(temp.stripTrailingZeros().toPlainString() + "");
           
        } catch (Exception e) {
            operatorProcess = true;
            f.getTxtDisplay().setText("ERROR");
        }
    }

    //nhấn nút operator set operator( + = 1; - = 2; x = 3; / = 4)
    public void setOperator(int operator) {
        this.operator = operator;
    }

    public void btnAdd() {
        f.getBtnAdd().addActionListener((e) -> {
            calculate();
            setOperator(1);
        });
    }

    public void btnSubtract() {
        f.getBtnSub().addActionListener((e) -> {
            calculate();
            setOperator(2);
        });
    }

    public void btnMultiply() {
        f.getBtnMul().addActionListener((e) -> {
            calculate();
            setOperator(3);
        });
    }

    public void btnDivide() {
        f.getBtnDiv().addActionListener((e) -> {
            calculate();
            setOperator(4);
        });
    }

    //nhấn nút kết quả 
    public void pressResult() {
        if (!f.getTxtDisplay().getText().equals("ERROR")) {
            calculate();// calculate() sẽ gọi lại kết quả để hiển thị
            operator = -1;//cài lại operator

            //nếu lỗi set về 0
        } else if (f.getTxtDisplay().getText().equals("ERROR")) {
            f.getTxtDisplay().setText("0");
            firstNum = new BigDecimal("0");
            secondNum = new BigDecimal("0");
        }
    }

    public void btnResult() {
        f.getBtnEqual().addActionListener((e) -> {
            pressResult();
        });
    }

    /*
    m+ thêm vào giá trị được lưu trữ trong bộ nhớ
    m- trừ đi giá trị được lưu trữ trong bộ nhớ
    mr display giá trị trong memory
    mc clear the value in memory
    
    */
    //nhấn nút %
    public void pressPercent() {
        if (f.getTxtDisplay().getText().equals("ERROR")) {
            Clear();
        }
        String value = f.getTxtDisplay().getText();
        BigDecimal xValue = new BigDecimal(value);
        BigDecimal percent = new BigDecimal(100);
        try {
            f.getTxtDisplay().setText(xValue.divide(percent, MathContext.DECIMAL64).stripTrailingZeros().toPlainString());
 //         f.getTxtDisplay().setText(getValue().doubleValue()/100 + "");
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROR");
        }
        operatorProcess = false;
        resetNumber = true;
    }

    public void btnPercent() {
        f.getBtnPercent().addActionListener((e) -> {
            pressPercent();
        });
    }

    //nhấn m+
    public void pressMadd() {
        try {
            //cộng giá trị từ màn hình vào memory
            memory = memory.add(getValue());
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROE");
        }
        //để nhấm 1 new number
        resetNumber = true;
    }

    // nhấm m-
    public void pressMsub() {
        try {
            memory = memory.subtract(getValue());
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROR");
        }
        resetNumber = true;
    }

    //nhấm mc
    public void pressMC() {
        memory = new BigDecimal("0");
    }

    //nhấn mr
    public void pressMR() {
        f.getTxtDisplay().setText(memory + "");
        resetNumber = true;//để nhấn 1 new number
        operatorProcess = false;//đẻ calculate can set first number = value of mr
    }

    public void btnMAdd() {
        f.getBtnMAdd().addActionListener((e) -> {
            pressMadd();
            f.getBtnMR().setBackground(Color.red);//để nhắc nhở người dùng rằng có giá trị trong bộ nhớ
            f.getBtnMR().setEnabled(true);//cho phép user to click to seevalue in memory
        });
    }

    public void btnMSub() {
        f.getBtnMSub().addActionListener((e) -> {
            pressMsub();
            f.getBtnMR().setBackground(Color.red);
            f.getBtnMR().setEnabled(true);
        });
    }

    public void btnMR() {
        f.getBtnMR().addActionListener((e) -> {
            pressMR();
        });
    }

    public void btnMC() {
        f.getBntMC().addActionListener((e) -> {
            pressMC();
            f.getBtnMR().setBackground(new Color(255, 233, 148));
            f.getBtnMR().setEnabled(false);
        });
    }

    //nhấn nút 1/x
    public void pressInvert() {
        if (f.getTxtDisplay().getText().equals("ERROR")) {
            Clear();
        }
        try {
            if (getValue().doubleValue() != 0) {//lỗi khi 1 số chia cho 0
                //
                BigDecimal numberInvert = new BigDecimal(f.getTxtDisplay().getText());
                BigDecimal numberOne = new BigDecimal(1);
                BigDecimal ans = numberOne.divide(numberInvert, new MathContext(17));
                //set kết quả ra màn hình
                f.getTxtDisplay().setText(ans.stripTrailingZeros().toPlainString());
            } else {
                f.getTxtDisplay().setText("ERROR");
            }
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROR");
        }
        operatorProcess = false;
        resetNumber = true;
    }

    // nhấn sqrt
    public void pressSqrt() {
        if (f.getTxtDisplay().getText().equals("ERROR")) {
            Clear();
        }
        try {
            double result = getValue().doubleValue();
            if (result >= 0) {
                double xResult = Math.sqrt(result);
                BigDecimal ans = new BigDecimal(xResult);
                //trả kết quả ra màn hình
                f.getTxtDisplay().setText(ans.add(new BigDecimal(0), new MathContext(17)).stripTrailingZeros().toPlainString());
            } else {
                f.getTxtDisplay().setText("ERROR");
            }
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROR");
        }
        operatorProcess = false;
        resetNumber = true;
    }

    //nhấn nút đổi dấu
    public void pressNegate() {
        if (f.getTxtDisplay().getText().equals("ERROR")) {
            Clear();
        }
        try {//đổi dấu giá trị trên màn hình
            f.getTxtDisplay().setText(getValue().negate() + "");
        } catch (Exception e) {
            f.getTxtDisplay().setText("ERROR");
        }
        operatorProcess = false;//để nhấn new operator
        resetNumber = true;//để nhấn 1 new number
    }
    
    public void btnNegate(){
        f.getBtnNega().addActionListener((e) -> {
            pressNegate();
        });
    }
    
    public void btnInvert(){
         f.getBtnInvert().addActionListener((e) -> {
           pressInvert();
        });
    }
    
    public void btnSqrt(){
         f.getBtnSqrt().addActionListener((e) -> {
           pressSqrt();
        });
    }
}
