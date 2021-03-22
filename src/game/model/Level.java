package game.model;

import tree.Node;

import java.util.List;

public class Level {
    private Integer level;
    private List<Node> nodes;
    private Integer visited = 0;

    public Level(Integer level, List<Node> nodes) {
        this.level = level;
        this.nodes = nodes;
    }

    public Integer getLevel() {
        return level;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public Integer getVisited() {
        return visited;
    }

    public void visit() {
        visited++;
    }

    public void add(Node node) {
        this.nodes.add(node);
    }
}
