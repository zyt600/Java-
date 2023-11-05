import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * ResultPanel
 * - Panel for results of searching, including schemes and Baidu suggestions
 */
public class ResultPanel extends JPanel {
    private JPanel schedulePanel;
    private JPanel baiduPanel;

    /**
     * Constructor
     */
    public ResultPanel() {
        setLayout(new GridLayout(2, 1));
    }

    /**
     * build
     * - create and build a new panel when a keyword is searched
     */
    void build(String keyword) {
        // remove the old elements
        removeAll();
        repaint();
        updateUI();

        // build and add the two sub-panels
        buildSchedulePanel(keyword);
        buildBaiduPanel(keyword);
        add(schedulePanel);
        add(baiduPanel);
    }

    /**
     * buildSchedulePanel
     * - build a panel for results of scheme searching
     */
    private void buildSchedulePanel(String keyword) {
        DBAccess db = new DBAccess();
        schedulePanel = new JPanel();
        List<Scheme> results = db.searchByTitleAndDescription(keyword);
        JLabel scheduleLabel = new JLabel("日程搜索的结果：");
        JPanel[] schemes = new JPanel[results.size()];

        schedulePanel.setLayout(new GridLayout(results.size() + 1, 1));
        schedulePanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        schedulePanel.add(scheduleLabel);

        // show the results
        for(int idx = 0; idx < results.size(); idx++) {
            schemes[idx] = new JPanel();
            schemes[idx].add(new JLabel(results.get(idx).getTitle()));
            schemes[idx].add(new JLabel(results.get(idx).getType()));
            schemes[idx].add(new JLabel(results.get(idx).getDatetime()));
            schemes[idx].add(new JLabel(results.get(idx).getDescription()));
            schedulePanel.add(schemes[idx]);
        }
    }


    /**
     * buildBaiduPanel
     * - build a panel for suggestions from baidu
     */
    private void buildBaiduPanel(String searchText) {
        baiduPanel = new JPanel();
        String[] suggestions = Suggestion.getSuggestion(searchText);
        JLabel baiduLabel = new JLabel("百度搜索的结果：");
        JButton[] baiduButtons = new JButton[5];

        baiduPanel.setLayout(new GridLayout(6, 1));
        baiduPanel.setBorder(BorderFactory.createLineBorder(Color.black, 2));
        baiduPanel.add(baiduLabel);

        // show the suggestions
        for (int idx = 0; idx < 5; idx++) {
            baiduButtons[idx] = new JButton();
            assert suggestions != null;
            if (idx >= suggestions.length) {
                continue;
            }
            baiduButtons[idx].setText(suggestions[idx]);
            baiduPanel.add(baiduButtons[idx]);
        }

        // if button clicked, browser will get open and the suggestion word will get searched in Baidu
        baiduButtons[0].addActionListener(e-> Suggestion.searchSuggestion(suggestions[0]));
        baiduButtons[1].addActionListener(e-> Suggestion.searchSuggestion(suggestions[1]));
        baiduButtons[2].addActionListener(e-> Suggestion.searchSuggestion(suggestions[2]));
        baiduButtons[3].addActionListener(e-> Suggestion.searchSuggestion(suggestions[3]));
        baiduButtons[4].addActionListener(e-> Suggestion.searchSuggestion(suggestions[4]));
    }
}
