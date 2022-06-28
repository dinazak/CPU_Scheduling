import javax.swing.*;
import java.awt.*;
import javax.swing.JFrame;
import javax.swing.border.Border;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;


class ColorRenderer extends JLabel implements TableCellRenderer  {
    public static ArrayList<Color> allColors ;
    public ColorRenderer(ArrayList<Color> Colors) {
        setOpaque(true);
        allColors = (ArrayList<Color>)Colors.clone();
    }

    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        for (int i=0;i<allColors.size();i++ )
        {
            if(row==i)
            {
                setBackground(allColors.get(i));
            }

        }

        return this;
    }
}


public class GUI {
    JFrame frame;
    JPanel p;
    JScrollPane right;
    GridBagConstraints c ;
    JTable T;
    public XYSeriesCollection dataset = new XYSeriesCollection();
    public GUI(int N)
    {

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500,500);

        p = new JPanel();
        p.setLayout(new GridBagLayout());
        p.setBackground(Color.darkGray);

        right = new JScrollPane();
        c = new GridBagConstraints();
        c.insets = new Insets(0, 0, 0, 0);
        c.gridx = 1;
        c.gridy = 0;

        String[] columnNames = { "Process", "Color", "Name","Priority" };
        T = new JTable(N,4);
        T.getColumnModel().getColumn(0).setHeaderValue("Process");
        T.getColumnModel().getColumn(1).setHeaderValue("Column");
        T.getColumnModel().getColumn(2).setHeaderValue("Name");
        T.getColumnModel().getColumn(3).setHeaderValue("Priority");

        Border blackline = BorderFactory.createLineBorder(Color.DARK_GRAY,5);
        right.setBackground(Color.gray);
        right.setBorder(blackline);
        right.setViewportView(T);
        p.add(right, c);
        frame.add(p);
        frame.setVisible(true);
    }
    public void setSeries(int processNo,ArrayList<Double> time)
    {


        String name ="Process "+(processNo+1);
        XYSeries S=new XYSeries(name);
        for (Double x : time) {
            Double process = (double) processNo + 1;
            S.add(x, process);
        }
        dataset.addSeries(S);
    }
    public void createChart(XYDataset dataset, ArrayList<Color> Colors) {

        JFreeChart chart = ChartFactory.createScatterPlot(
                "CPU Scheduling Graph", "Time", "Processes", dataset,
                PlotOrientation.VERTICAL, true, true, false);

        chart.setBackgroundPaint(Color.white);
        XYPlot plot = (XYPlot) chart.getPlot();
        Rectangle r = new Rectangle(0,0,15, 15);
        plot.setBackgroundPaint(new Color(0xffffe0));
        plot.setDomainGridlinesVisible(true);
        plot.setDomainGridlinePaint(Color.lightGray);
        plot.setRangeGridlinePaint(Color.lightGray);
        XYItemRenderer renderer = (XYItemRenderer) plot.getRenderer();
        for (int i=0;i<Colors.size();i++)
        {
            renderer.setSeriesPaint(i,Colors.get(i));
            renderer.setSeriesShape(i,r);
        }
        plot.setRenderer(renderer);

        ChartPanel cp = new ChartPanel(chart);
        c.gridx = 0;
        c.gridy = 0;
        p.add(cp, c);
    }
    public void setColor( ArrayList<Color> Colors)
    {
        T.getColumnModel().getColumn(1).setCellRenderer(new ColorRenderer(Colors));
    }
    public void setName( ArrayList<String> names)
    {
        String counter;
        for (int i=0;i<names.size();i++)
        {
            counter= String.valueOf(i+1);
            T.setValueAt(names.get(i),i,2);
            T.setValueAt(counter,i,0);
        }

    }
    public void setPriority( ArrayList<Integer> priority)
    {
        for (int i=0;i<priority.size();i++)
        {
            T.setValueAt(priority.get(i),i,3);
        }

    }

}
