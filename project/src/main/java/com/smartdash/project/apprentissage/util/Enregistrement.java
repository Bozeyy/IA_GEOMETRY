package com.smartdash.project.apprentissage.util;

import com.smartdash.project.IA.Module;
import com.smartdash.project.IA.Neurone;
import com.smartdash.project.IA.NeuroneFabrique;
import com.smartdash.project.IA.Reseau;
import com.smartdash.project.modele.Joueur;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class Enregistrement {

    public static String debutEnregistrement() throws Exception {
        //On vérifie si le dossier <enregistrement> existe
        if(!new File("src/main/resources/enregistrement").exists()) new File("src/main/resources/enregistrement").mkdir();

        //Récupération de la date et du temps ou le programme a été lancé
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy_HH-mm-ss");
        Date date = new Date();
        String dateHeureCourante = format.format(date);

        System.out.println("Date de début d'apprentissage : " + dateHeureCourante);

        //création du dossier de sauvegarde de l'apprentissage
        String pathname = "src/main/resources/enregistrement/" + dateHeureCourante;
        File dossier = new File(pathname);

        try {
            if (!dossier.exists()) {
                if (dossier.mkdir()) {
                    System.out.println("Dossier créé à l'emplacement : " + pathname);
                    return pathname;
                } else {
                    throw new Exception("Erreur lors de la création du dossier");
                }
            } else {
                throw new Exception("Le dossier existe déjà");
            }
        } catch (SecurityException e) {
            System.out.println(e.getMessage());
        }

        return "";
    }

    public static void generationEnregistrement(String pathname, int generation, List<Joueur> population) throws Exception {

        //création du fichier de sauvegarde d'une generation
        String pathnameGeneration = pathname + "/generation_" + generation + ".txt";
        File fichier = new File(pathnameGeneration);

        //On rempli le fichier avec la population en chaîne de caractère
        String popToStrong = populationToString(population);

        //On écrit dans le fichier
        FileWriter writer = new FileWriter(fichier);
        writer.write(popToStrong);

        //On ferme le fichier
        writer.close();

    }

    public static Joueur recupererJoueurGeneration(String pathname,int joueur) throws Exception {

        //On retourne la population
        List<Joueur> population = Enregistrement.stringToPopulation(pathname);

        //On récupère le joueur
        if(joueur < population.size())
        {
            return new Joueur( population.get(joueur).getReseau().clone());
        }
        else
            throw new IllegalArgumentException("Le joueur n'a pas été trouvé");

    }

    public static List<Joueur> stringToPopulation(String pathname) throws IOException {

        try {

            //On récupère le fichier
            BufferedReader r = new BufferedReader(new FileReader(pathname));

            //On créer la population
            List<Joueur> population = new ArrayList<>();

            //On lit la première ligne
            String line = r.readLine();

            //On parcourt le fichier
            while (line != null) {

                //On récupère les attributs
                String[] attributs = line.split("\\|");

                //On récupère le score
                double score = Double.parseDouble(attributs[1]);

                //On récupère le reseau
                Reseau reseau = new Reseau();

                //On récupère les modules
                String[] modules = attributs[2].split("_");

                //On parcourt les modules
                for (String module : modules) {

                    //On récupère les neurones
                    String[] neurones = module.split("/");

                    //On créer un module
                    Module m = new Module();

                    //On parcourt les neurones
                    for (String neurone : neurones) {

                        //On récupère les coordonnées et le type
                        String[] attributsNeurone = neurone.split(",");

                        //On ajoute les attributs au neurone
                        int x = Integer.parseInt(attributsNeurone[0]);
                        int y = Integer.parseInt(attributsNeurone[1]);
                        char type = attributsNeurone[2].charAt(0);

                        //On ajoute le neurone au module
                        m.addNeurone(NeuroneFabrique.genererNeuronneType(x, y, type));
                    }
                    //On ajoute le module au reseau
                    reseau.addModule(m);
                }
                //On ajoute le joueur à la population
                population.add(new Joueur(reseau));

                //On lit la ligne suivante
                line = r.readLine();
            }

            //On retourne la population
            return population;
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    public static String populationToString(List<Joueur> population) {

        //On trie la population en fonction de leur resultat (decroissant)
        population.sort(Comparator.comparing(Joueur::getScore).reversed());

        //On créer un stringbuilder pour créer la chaine de caractère
        StringBuilder sb = new StringBuilder();

        //Initialisation du rang
        int rang = 1;

        //On parcourt la population
        for (Joueur j : population) {

            //Rang
            sb.append(rang);

            //Séparateur attribut
            sb.append("|");

            //Score
            sb.append(j.getScore());

            //Séparateur attribut
            sb.append("|");

            //On récupère le reseau du joueur
            Reseau r = j.getReseau();

            //On parcourt les modules du reseau
            int separateurModule = 0;
            for(Module m : r.getModules()) {

                //On parcourt les neurones du module
                for (Neurone n : m.getNeurones()) {

                    //Coordonnée X
                    sb.append(n.getX());

                    //Séparateur attribut neurone
                    sb.append(",");

                    //Coordonnée Y
                    sb.append(n.getY());

                    //Séparateur attribut neurone
                    sb.append(",");

                    //Type
                    sb.append(n.getType());

                    //Séparateur neurone
                    if (m.getNeurones().indexOf(n) != m.getNeurones().size() - 1) sb.append("/");
                }
                //Séparateur module
                if (separateurModule < r.getModules().size() -1) sb.append("_");
                separateurModule++;

            }

            //Séparateur joueur
            sb.append("\n");

            //On incrémente le rang
            rang++;
        }

        //On retourne la chaine de caractère
        return sb.toString();
    }

}
