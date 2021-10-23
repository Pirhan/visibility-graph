package pison;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Point;
import org.newdawn.slick.geom.Polygon;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.util.Bootstrap;

import pison.visibilitygraph.VisibilityGraph;
import pison.visibilitygraph.VisibilityGraphPanel;

public class VisibilityGraphDemo extends StateBasedGame {
    private VisibilityGraphPanel vgp;
    private int gridPanelState = 0;

    public VisibilityGraphDemo(String name, VisibilityGraphPanel vgp) {
        super(name);

        this.vgp = vgp;
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        addState(vgp);
        enterState(gridPanelState);
    }

    public static void main(String[] args) {
        VisibilityGraph vg = buildDemoGraph();
        VisibilityGraphPanel vgp = new VisibilityGraphPanel(vg);
        VisibilityGraphDemo pfd = new VisibilityGraphDemo("VisibilityGraph Graph", vgp);

        vgp.prepareForRender();

        Bootstrap.runAsApplication(pfd, 600, 400, false);
    }

    private static VisibilityGraph buildDemoGraph() {
        Polygon p1 = new Polygon();
        Polygon p2 = new Polygon();
        Polygon p3 = new Polygon();
        Polygon p4 = new Polygon();
        Polygon p5 = new Polygon();
        Polygon p6 = new Polygon();
        Polygon p7 = new Polygon();

        p1.addPoint(70, 30);
        p1.addPoint(20, 60);
        p1.addPoint(80, 90);

        p2.addPoint(540, 310);
        p2.addPoint(520, 350);
        p2.addPoint(490, 350);
        p2.addPoint(460, 310);
        p2.addPoint(480, 270);
        p2.addPoint(520, 270);

        p3.addPoint(220, 170);
        p3.addPoint(180, 180);
        p3.addPoint(230, 300);
        p3.addPoint(270, 290);

        p4.addPoint(290, 330);
        p4.addPoint(220, 330);
        p4.addPoint(235, 360);
        p4.addPoint(270, 360);

        p5.addPoint(60, 120);
        p5.addPoint(160, 200);
        p5.addPoint(90, 90);

        p6.addPoint(300, 150);
        p6.addPoint(480, 200);
        p6.addPoint(400, 300);

        p7.addPoint(400, 160);
        p7.addPoint(460, 40);
        p7.addPoint(300, 20);
        p7.addPoint(300, 120);

        Point start = new Point(100, 360);
        Point end = new Point(560, 50);

        return new VisibilityGraph(start, end, p1, p2, p3, p4, p5, p6, p7);
    }
}
