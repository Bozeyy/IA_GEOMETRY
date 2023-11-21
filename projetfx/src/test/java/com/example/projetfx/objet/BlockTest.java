package com.example.projetfx.objet;

import com.example.projetfx.modele.Joueur;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BlockTest {

    @Test
    void testIsInside() {
        Joueur joueur = new Joueur(0, 0);
        Block block = new Block(-39, -39);
        assertTrue(block.isInside(joueur));
        block.x = 39;
        block.y = 39;
        assertTrue(block.isInside(joueur));
        block.x = -39;
        block.y = 39;
        assertTrue(block.isInside(joueur));
        block.x = 39;
        block.y = -39;
        assertTrue(block.isInside(joueur));
    }

}