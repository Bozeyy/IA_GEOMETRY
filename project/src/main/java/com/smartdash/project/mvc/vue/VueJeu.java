package com.smartdash.project.mvc.vue;


import com.smartdash.project.mvc.modele.Jeu;
import com.smartdash.project.mvc.modele.Sujet;
import com.smartdash.project.mvc.vue.VueNeurone.NeuroneVue;
import com.smartdash.project.mvc.modele.objet.Bloc;
import com.smartdash.project.mvc.modele.objet.piques.Pique;
import com.smartdash.project.mvc.modele.objet.piques.PiqueDroit;
import com.smartdash.project.mvc.modele.objet.piques.PiqueGauche;
import com.smartdash.project.mvc.modele.objet.piques.PiqueRetourne;
import com.smartdash.project.mvc.vue.VuePique.VuePique;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueDroit;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueGauche;
import com.smartdash.project.mvc.vue.VuePique.VuePiqueRetourne;
import javafx.scene.effect.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class VueJeu extends Pane implements Observateur {
    private Jeu modele;
    private Color couleurNiveau;
    private VueReseau vueReseau;
    private VueJoueur vueJoueur;
    private VueParticule vueParticule;

    public VueJeu(Jeu donnees, String couleur) throws Exception {
        this.modele = donnees;
        this.vueReseau = new VueReseau(modele);
        if(couleur == null)
            getRandomColor();
        else {
            switch (couleur) {
                case "Rouge" -> couleurNiveau = Color.RED;
                case "Bleu" -> couleurNiveau = Color.DARKBLUE;
                case "Noir" -> couleurNiveau = Color.BLACK;
                default -> couleurNiveau = Color.BLACK;
            }
        }
        //Taille de base de la fenêtre de jeu
        setPrefSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur() - 5 * this.modele.getTailleCase());

        //setMinSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur());

        //Taille maximale de la fenêtre de jeu
        setMaxSize(donnees.getTerrain().getLongueur() * this.modele.getTailleCase(), donnees.getTailleCase() * donnees.getTerrain().getLargeur() - 5 * this.modele.getTailleCase());
    }

    /**
     * Méthode qui permet de tout initialiser
     */
    public void init() throws FileNotFoundException {
        //On vide la fenêtre de jeu
        getChildren().clear();

        String imagePath = "/background2.png"; // Assurez-vous que le chemin commence par un '/'
        InputStream is = getClass().getResourceAsStream(imagePath);
        if (is == null) {
            throw new FileNotFoundException("background2.png");
        }
        Image image = new Image(is);

        // Créer un conteneur HBox pour positionner plusieurs images côte à côte
        HBox backgroundContainer = new HBox();

        double tailleFond = 0;
        double imageWidth = image.getWidth();

        while (tailleFond < this.modele.getTerrain().getLongueur() * this.modele.getTailleCase() + imageWidth) {
            VBox vBox = new VBox();


            ImageView backgroundImageView = new ImageView(image);
            backgroundImageView.setFitHeight(19*modele.getTailleCase());

            Rectangle sol = new Rectangle();
            sol.setWidth(image.getWidth());
            sol.setHeight(modele.getTailleCase() * modele.getTerrain().getLargeur());
            sol.setFill(Color.GREY);

            Blend blend = new Blend();
            blend.setMode(BlendMode.ADD);

            // Couleur de la lueur
            ColorInput colorInput = new ColorInput(0, 0, image.getWidth(), image.getHeight(), couleurNiveau);

            // Application de l'effet de lueur à l'image
            blend.setTopInput(colorInput);
            backgroundImageView.setEffect(blend);
            backgroundImageView.setPreserveRatio(false);
            backgroundImageView.setFitWidth(imageWidth);  // Réglage de la largeur réelle

            vBox.getChildren().addAll(backgroundImageView, sol);
            backgroundContainer.getChildren().add(vBox);
            tailleFond += imageWidth;
        }

        // Ajouter le Rectangle et l'ImageView à la scène
        getChildren().addAll(backgroundContainer);

        // On ajoute le joueur
        vueJoueur = new VueJoueur(modele, modele.getJoueur().getX(), modele.getJoueur().getY());
        getChildren().add(vueJoueur);

        vueParticule = new VueParticule(modele);
        getChildren().add(vueParticule);

            //On ajoute les blocs
            this.modele.getTerrain().getMap().forEach(objet -> {
                if (objet instanceof Bloc) {
                    getChildren().add(new VueBloc(modele, objet.getX(), objet.getY(), this.couleurNiveau,0));
                } else if (objet instanceof PiqueRetourne) {
                    getChildren().add(new VuePiqueRetourne(modele, objet.getX(), objet.getY(), this.couleurNiveau,0));
                } else if (objet instanceof PiqueGauche) {
                    getChildren().add(new VuePiqueGauche(modele, objet.getX(), objet.getY(), this.couleurNiveau,0));
                } else if (objet instanceof PiqueDroit) {
                    getChildren().add(new VuePiqueDroit(modele, objet.getX(), objet.getY(), this.couleurNiveau,0));
                } else if (objet instanceof Pique) {

                    getChildren().add(new VuePique(modele, objet.getX(), objet.getY(), this.couleurNiveau,0));

                }
            });

            //On met à jour la caméra
            vueJoueur.translateXProperty().addListener((obs, old, newValue) -> {
                int offset = newValue.intValue();
                System.out.println();
                if (offset > 400 && offset < (modele.getTerrain().getLongueur() * modele.getTailleCase()) - 400) {
                    setTranslateX(-(offset - 400));
                } else if (offset < 400) {
                    setTranslateX(0);
                } else {
                    setTranslateX(-(modele.getTerrain().getLongueur() * modele.getTailleCase() - 800));
                }
            });

            //On met à jour la rotation du joueur
            vueJoueur.yProperty().addListener((obs, old, newValue) -> {
                animationSaut();
            });


            // ajout des neurone au pane
            for (NeuroneVue neuroneVue : vueReseau.getNeurones()) {
                this.getChildren().add(neuroneVue.getShape());
            }
    }

    private void getRandomColor() {
        List<Color> listCouleurs = new ArrayList<>();

        listCouleurs.add(Color.BLACK);
        listCouleurs.add(Color.RED);
        listCouleurs.add(Color.DARKBLUE);


        Random random = new Random();
        couleurNiveau = listCouleurs.get(random.nextInt(listCouleurs.size()));
    }

    public void animationSaut(){
        vueJoueur.animationSaut();
    }

    public Color getCouleurNiveau() {
        return couleurNiveau;
    }

    /**
     * Méthode qui permet d'actualiser à chaque temps
     * @param sujet le modele
     */
    @Override
    public void actualiser(Sujet sujet) {
        Jeu jeu = (Jeu) sujet;

        if(jeu.getJoueur().getVivant())
        {
            vueJoueur.actualiser();
            vueReseau.actualiser(sujet);
            vueParticule.actualiser();
        }
    }
}
