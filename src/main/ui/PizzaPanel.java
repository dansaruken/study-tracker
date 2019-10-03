package ui;

import javax.swing.*;
import java.awt.*;

public class PizzaPanel extends JPanel {
    private JTextField priceBox;
    private JCheckBox[] toppingsList;
    private JRadioButton mozza, cheddar;
    private JComboBox sizeChoice;

    public PizzaPanel() {
        toppingsList = new JCheckBox[4];
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        setPreferredSize(new Dimension(500, 200));
        setBorder(BorderFactory.createTitledBorder("Create your own pizza!"));

        add(createSizePanel());
        add(createCheesePanel());
        add(createToppingsPanel());

        JPanel output = new JPanel();
        output.add(new JLabel("Total Price: $"));
        priceBox = new JTextField(5);
        priceBox.setEditable(false);
        output.add(priceBox);
        add(output);


    }

    public JPanel createCheesePanel() {
        mozza = new JRadioButton("Mozzarella");
        mozza.addActionListener((e) ->
        {
            priceBox.setText("" + calculateCost());
        });
        cheddar = new JRadioButton("Cheddar");
        cheddar.addActionListener((e) ->
        {
            priceBox.setText("" + calculateCost());
        });

        ButtonGroup cheeses = new ButtonGroup();
        cheeses.add(mozza);
        cheeses.add(cheddar);
        JPanel radioPanel = new JPanel();
        radioPanel.add(mozza);
        radioPanel.add(cheddar);
        return radioPanel;
    }

    public JPanel createSizePanel() {
        String[] sizes = {"Small", "Medium", "Large"};
        sizeChoice = new JComboBox(sizes);
        sizeChoice.addActionListener((e) ->
        {
            priceBox.setText("" + calculateCost());
        });

        JPanel organizer = new JPanel();
        organizer.add(sizeChoice);
        return organizer;
    }

    public JPanel createToppingsPanel() {
        toppingsList[0] = new JCheckBox("Pepperoni");
        toppingsList[1] = new JCheckBox("Mushroom");
        toppingsList[2] = new JCheckBox("Onion");
        toppingsList[3] = new JCheckBox("Red Pepper");
        JPanel organizer = new JPanel(new GridLayout(2, 2));
        organizer.setBorder(BorderFactory.createTitledBorder("Select Toppings"));
        for (JCheckBox toppingBox : toppingsList) {
            toppingBox.addActionListener((e) ->
            {
                priceBox.setText("" + calculateCost());
            });
            organizer.add(toppingBox);
        }
        return organizer;
    }

    public int calculateCost() {
        int price = 6;
        if (sizeChoice.getSelectedItem() == "Medium") {
            price += 2;
        } else if (sizeChoice.getSelectedItem() == "Large") {
            price += 5;
        }
        for (JCheckBox toppingBox : toppingsList) {
            if (toppingBox.isSelected()) {
                price += 1;
            }
        }
        if (mozza.isSelected()) {
            price += 1;
        }
        return price;
    }


}

