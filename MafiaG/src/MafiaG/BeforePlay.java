package MafiaG;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BeforePlay extends Frame {
    private int readyCount = 0;
    private final int totalPlayers = 1;

    public BeforePlay() {
        setTitle("MafiaG");
        setLayout(new BorderLayout());

        // 창 닫기 이벤트
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        // 상단 바 - 로고 + 랭킹 닉네임
        Panel topBar = new Panel(new BorderLayout());
        ImageIcon logoIcon = new ImageIcon("src/images/MafiaG_wordlogo.jpg");
        setIconImage(logoIcon.getImage());
        JLabel logo = new JLabel(logoIcon);
        topBar.add(logo, BorderLayout.WEST);

        Panel rightInfo = new Panel(new FlowLayout(FlowLayout.RIGHT));
        JLabel rank = new JLabel("랭킹");
        JLabel nickname = new JLabel("닉네임");
        rightInfo.add(rank);
        rightInfo.add(nickname);
        topBar.add(rightInfo, BorderLayout.EAST);

        add(topBar, BorderLayout.NORTH);

        // 왼쪽 - 랭킹 + 참여자 + 시작 버튼
        Panel leftPanel = new Panel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        Label rankingLabel = new Label("랭킹", Label.CENTER);
        rankingLabel.setBackground(new Color(230, 220, 250));
        rankingLabel.setPreferredSize(new Dimension(150, 50));

        TextArea participants = new TextArea("참여자 명단\nuser01\nuser02");
        participants.setEditable(false);
        participants.setPreferredSize(new Dimension(150, 300));

        Button startBtn = new Button("시작");
        startBtn.setPreferredSize(new Dimension(150, 50));
        startBtn.setEnabled(true); // 임시로 활성화

        startBtn.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                readyCount++;
                startBtn.setEnabled(false);
                startBtn.setLabel("대기 중...");

                if (readyCount >= totalPlayers) {
                    dispose();
                    new MafiaGGame("user01");
                }
            }
        });

        leftPanel.add(rankingLabel);
        leftPanel.add(participants);
        leftPanel.add(startBtn);
        add(leftPanel, BorderLayout.WEST);

        // 중앙 - 튜토리얼 이미지 + 하단 텍스트
        Panel centerPanel = new Panel(new BorderLayout());

        ImageIcon tutorialImg = new ImageIcon("src/images/tutorial.jpg");
        JLabel tutorial = new JLabel("튜토리얼 이미지 노출", JLabel.CENTER);
        tutorial.setFont(new Font("맑은 고딕", Font.BOLD, 28));
        tutorial.setOpaque(true);
        tutorial.setBackground(new Color(240, 240, 250));
        tutorial.setPreferredSize(new Dimension(600, 400));

        Label waitLabel = new Label("참여자를 기다리는 중입니다...", Label.CENTER);
        waitLabel.setBackground(new Color(230, 240, 250));

        centerPanel.add(tutorial, BorderLayout.CENTER);
        centerPanel.add(waitLabel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        setSize(1200, 800);
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
