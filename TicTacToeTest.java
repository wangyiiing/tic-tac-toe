package tictaktoc;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TicTacToeTest {
	static TicTacToe frame;
	
	@BeforeEach
	void initial() {
		frame = new TicTacToe();
	}
	
	@Test
	void testBlackWon() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,0).D_alternate();
		frame.getCell(0, 1).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(2,2).D_alternate();
		assertTrue(frame.isWon('B'));
		assertFalse(frame.isWon('W'));
	}
	
	@Test
	void testWhiteWon() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,1).D_alternate();
		frame.getCell(0,0).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(2,1).D_alternate();
		frame.getCell(2,2).D_alternate();
		assertTrue(frame.isWon('W'));
		assertFalse(frame.isWon('B'));
	}
	
	@Test
	void testTie() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,0).D_alternate();
		frame.getCell(0,1).D_alternate();
		frame.getCell(0,2).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(1,2).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(2,0).D_alternate();
		frame.getCell(2,2).D_alternate();
		frame.getCell(2,1).D_alternate();
		assertTrue(frame.isFull());
		assertFalse(frame.isWon('W'));
		assertFalse(frame.isWon('B'));
	}
	
	@Test
	void testRestart() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,0).D_alternate();
		frame.getCell(0,1).D_alternate();
		frame.getCell(0,2).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(1,2).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(2,0).D_alternate();
		frame.getCell(2,2).D_alternate();
		frame.getCell(2,1).D_alternate();
		assertTrue(frame.isFull());
		frame.restart();
		assertFalse(frame.isFull());
	}
	
	@Test
	void testEvaluate1() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,0).D_alternate();
		frame.getCell(0, 1).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(2,2).D_alternate();
		assertEquals(frame.Evaluate_BW_Delta(),-1044);
	}
	
	@Test
	void testEvaluate2() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,1).D_alternate();
		frame.getCell(0,0).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(1,1).D_alternate();
		frame.getCell(2,1).D_alternate();
		frame.getCell(2,2).D_alternate();
		assertEquals(frame.Evaluate_BW_Delta(),1019);
	}
	
	@Test
	void testEvaluate3() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,1).D_alternate();
		frame.getCell(1,0).D_alternate();
		frame.getCell(0,2).D_alternate();
		frame.getCell(2,0).D_alternate();
		assertEquals(frame.Evaluate_BW_Delta(),-60);
	}
	
	@Test
	void testAlphaBeta() {
		frame.start();
		frame.setWhoseTurn('B');
		frame.getCell(0,0).alternate();
		assertEquals(frame.getCell(1, 1).getToken(),'W');
	}
}
