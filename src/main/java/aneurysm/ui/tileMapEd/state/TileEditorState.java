package aneurysm.ui.tileMapEd.state;

import aneurysm.ui.tileMapEd.tileMapEditor;

public class TileEditorState {

    private int currentHighlight = -1;
    private int selectedTileListIndex = 0;
    private int selectedTileMapIndex = -1;
    private int flipstate = 0;
    private tileMapEditor host;

    public TileEditorState(tileMapEditor host) {
        this.host = host;
    }

    public void updateHighlightedTiles(int tileIndex) {
        currentHighlight = tileIndex;
    }

    public void setSelectedTileListIndex(int selectedTileIndex) {
//        System.out.printf("setSelectedTileListIndex %04x\n", selectedTileIndex);
        this.selectedTileListIndex = selectedTileIndex;
    }

    public int getHighlightedTileIndex() {

        return currentHighlight;
    }

    public int getSelectedTileListIndex() {
        return selectedTileListIndex;
    }

    public int getSelectedTileMapIndex() {
        return selectedTileMapIndex;
    }

    public void setSelectedTileMapIndex(int selectedTileMapIndex) {
        this.selectedTileMapIndex = selectedTileMapIndex;
        this.selectedTileListIndex = host.getTilemap()[selectedTileMapIndex & 0x7ff];
    }


    public int getFlipState() {
        return this.flipstate;
    }

    public void setFlipState(int state) {
        this.flipstate = state;
    }

    public void incrementFlipState() {

        this.flipstate += 0x800;
        if (this.flipstate > 0x1800) {
            this.flipstate = 0x0;
        }
    }

    public void decrementFlipState() {

        this.flipstate -= 0x800;
        if (this.flipstate <= 0) {
            this.flipstate = 0x1800;
        }
//        System.out.printf("flipstate : %08x\n", this.flipstate);
    }
}
