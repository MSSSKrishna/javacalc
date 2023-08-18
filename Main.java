import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/**
 * Calculator app
 */
public class Main {

    public static void main(String[] args) {
        new Calculator("calculator");

    }
}

class Calculator extends JFrame implements ActionListener {
    JButton b0, bd, bpl, bpr, bpe, bpc, bpx;
    JLabel l;
    String i = "";
    JButton[] numberButtons;
    JButton[] operatorButtons;
    String[] operators = { "+", "-", "*", "/" };
    boolean resultshown = false;

    public void actionPerformed(ActionEvent ae) {
        JButton b = (JButton) ae.getSource();
        String a = b.getText();

        if (resultshown) {
            l.setText("");
            resultshown = false;
        }
        l.setText(l.getText() + a);

    }

    Calculator(String s) {
        setTitle(s);
        setVisible(true);
        setSize(800, 800);

        setLayout(new GridLayout(2, 2));

        JPanel pa = new JPanel(new GridLayout(1, 1));

        JPanel pb = new JPanel(new GridLayout(5, 4, 5, 5));

        pa.setBackground(Color.BLACK);
        pb.setBackground(Color.white);
        // pa.setForeground(Color.red);
        int j = 0;
        numberButtons = new JButton[10];

        operatorButtons = new JButton[10];
        for (int i = 1; i <= 9; i++) {
            numberButtons[i] = new JButton("" + i);
            numberButtons[i].addActionListener(this);
            if (i % 3 == 0) {
                pb.add(numberButtons[i]);
                operatorButtons[j] = new JButton("" + operators[j]);
                operatorButtons[j].addActionListener(this);
                pb.add(operatorButtons[j]);
                j++;
            } else {

                pb.add(numberButtons[i]);
            }
        }
        b0 = new JButton("0");
        bd = new JButton("/");
        b0.addActionListener(this);
        bd.addActionListener(this);
        bpc = new JButton("C");
        bpl = new JButton("(");
        bpr = new JButton(")");
        bpe = new JButton("=");
        bpx = new JButton("del");

        l = new JLabel();
        l.setFont(new Font("SANSSERIF", Font.BOLD, 60));
        l.setForeground(Color.red);
        pa.add(l);
        pb.add(bpl);
        pb.add(b0);
        pb.add(bpr);
        pb.add(bd);
        pb.add(new JButton());
        pb.add(bpx);
        pb.add(bpc);
        pb.add(bpe);
        add(pa);
        add(pb);
        bpl.addActionListener(this);
        bpr.addActionListener(this);
        bpe.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                try {
                    String expression = l.getText();
                    l.setText(Expression.evaluateExpression(expression));
                } catch (Exception e) {
                    l.setText("error");
                    resultshown = true;
                }
            }
        });
        bpx.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                if (l.getText().length() > 0) {
                    StringBuilder sb = new StringBuilder(l.getText());
                    sb.deleteCharAt(sb.length() - 1);
                    l.setText(sb.toString());
                }
            }
        });
        bpc.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                l.setText("");
            }
        });
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent we) {
                System.exit(0);
            }
        });
        addKeyListener(new KeyAdapter() {
            public void keyTyped(KeyEvent ke) {
                if (resultshown) {
                    l.setText("");
                    resultshown = false;
                }
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyChar() == '+' || ke.getKeyChar() == '-'
                        || ke.getKeyChar() == '*' || ke.getKeyChar() == '/') {
                    l.setText(l.getText() + ke.getKeyChar());
                } else if (ke.getKeyChar() == 'c') {
                    l.setText("");
                } else if (ke.getKeyChar() == KeyEvent.VK_ENTER) {
                    String expression = l.getText();
                    try {
                        l.setText(Expression.evaluateExpression(expression));
                    } catch (Exception e) {
                        l.setText("error");
                        resultshown = true;
                    }
                } else if (ke.getKeyChar() == KeyEvent.VK_BACK_SPACE) {
                    if (l.getText().length() > 0) {
                        StringBuilder sb = new StringBuilder(l.getText());
                        sb.deleteCharAt(sb.length() - 1);
                        l.setText(sb.toString());
                    }
                }
                System.out.println(ke.getKeyChar());
            }
        });
    }
}