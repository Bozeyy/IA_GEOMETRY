package com.smartdash.project.apprentissage;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.jfree.chart.*;
import com.smartdash.project.modele.Joueur;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Statistique {

    private List<List<Joueur>> generations = new ArrayList<>();

    // Méthode pour calculer la moyenne des scores des joueurs dans une liste
    public static double calculerMoyenneDesScores(List<Joueur> joueurs) throws Exception {
        if (joueurs == null || joueurs.isEmpty()) {
            throw new Exception("Joueur null existant");
        }

        double sommeDesScores = 0;

        for (Joueur joueur : joueurs) {
            if(joueur.getScore()<0) throw new Exception("Score inférieur à 0");
            sommeDesScores += joueur.getScore();
        }

        return (sommeDesScores) / (joueurs.size());
    }

    public static double calculerMoyenne10Meilleurs(List<Joueur> joueurs) throws Exception {

        List<Joueur> copieJoueurs = new ArrayList<>(joueurs);
        copieJoueurs.sort(Comparator.comparingDouble(Joueur::getScore).reversed());


        double sommeDesScores = 0;

        for (int i = 0; i < 10; i++) {
            Joueur joueur = copieJoueurs.get(i);
            if(joueur.getScore()<0) throw new Exception("Score inférieur à 0");
            sommeDesScores += joueur.getScore();
            System.out.println("Score : "+joueur.getScore());
//            System.out.println(joueur.getReseau());
//            System.out.println(joueur.getScore());
        }

        return (sommeDesScores) / (10);
    }

    public void addGeneration (List<Joueur> joueurs) {
        this.generations.add(joueurs);
    }

    public void genererPDF () {
        try {
            PDDocument document = new PDDocument();

            for (int i = 0; i < generations.size(); i++) {
                PDPage page = new PDPage();
                document.addPage(page);

                genererPageGeneration(document, page, i);
            }

//            PDPage page = new PDPage();
//            document.addPage(page);
//            genererPageGraphique(document, page);

            document.save("statistiques.pdf");
            document.close();

            System.out.println("PDF géréré");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

//    private void genererPageGraphique(PDDocument document, PDPage page) throws IOException {
//        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
//            JFreeChart chart = creerGraphique(generations);
//            ChartPanel chartPanel = new ChartPanel(chart);
//            chartPanel.setPreferredSize(new Dimension(500, 400));
//
//            // Convertir le graphique en image
//            Image image = chartPanel.createImage(chartPanel.getWidth(), chartPanel.getHeight());
//            ImageIcon imageIcon = new ImageIcon((Image) image);
//
//            // Dessiner l'image sur la page PDF
//            contentStream.drawImage(imageIcon.getImage(), 50, 150);
//        }
//    }

    private void genererPageGeneration(PDDocument document, PDPage page, int numGeneration) {
        List<Joueur> generation = this.generations.get(numGeneration);

        try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
            contentStream.setFont(PDType1Font.HELVETICA_BOLD, 12);
            contentStream.beginText();
            contentStream.newLineAtOffset(100, 700);
            contentStream.showText("Génération: " + numGeneration);
            contentStream.newLine();
            contentStream.showText("Moyenne du score: " + calculerMoyenneDesScores(generation));
            contentStream.endText();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

//    private JFreeChart creerGraphique() {
//        JFreeChart chart = ChartFactory.createLineChart(
//                "Moyenne du score par génération",
//                "Génération",
//                "Moyenne du score",
//                creerDataset());
//
//        XYPlot plot = (XYPlot) chart.getPlot();
//        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
//        yAxis.setRange(0, 100);
//
//        XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
//        plot.setRenderer(renderer);
//
//        return chart;
//    }
//
//    private org.jfree.data.xy.XYDataset creerDataset() {
//        org.jfree.data.xy.XYSeries series = new org.jfree.data.xy.XYSeries("Moyenne du score");
//
//        for (List<Joueur> stat : generations) {
//            series.add(stat.getNumero(), stat.getMoyenneScore());
//        }
//
//        return new org.jfree.data.xy.XYSeriesCollection(series);
//    }

}
