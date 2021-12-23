package util;

public class Piece {
	protected boolean white;
	private boolean dummy;
	String name="";
	public Piece(boolean white, boolean dummy) {
		this.white = white;
		this.dummy = dummy;
	}
	public Piece(boolean white, boolean dummy, String name) {
		this.white = white;
		this.dummy = dummy;
		this.name = name;
	}
	public void setPiece(String name) {
		name = name;
	}
	public String getPiece() {
		return name;
	}
	public String toString() {
		return (white?"W " + name: "B " + name);
	}
	public boolean isEmpty() {
	    return dummy;
	}
}
