package crosemont.dti.g55.application_gestion_de_stage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.présentation.modèles.Modèle
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.DetailsOffrePrésentateur

class details_offre : Fragment() {

    lateinit var titre: TextView
    lateinit var employeur: TextView
    lateinit var modeEmploi: TextView
    lateinit var description: TextView
    lateinit var postulerButton: Button
    private lateinit var btnAccueil: ImageButton
    private lateinit var navController: NavController
    private lateinit var présentateur: DetailsOffrePrésentateur

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_details_offre, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        titre = view.findViewById(R.id.textViewTitrePoste)
        employeur = view.findViewById(R.id.textViewEmployeur)
        modeEmploi = view.findViewById(R.id.textViewModeEmploi)
        description = view.findViewById(R.id.textViewDescription)
        btnAccueil = view.findViewById(R.id.btn_accueil)
        postulerButton = view.findViewById(R.id.postulerButton)
        présentateur = DetailsOffrePrésentateur(this)
        if(offre != null) {
            titre.text = "Tite du poste : " + offre!!.titrePoste
            employeur.text = "Entreprise : " + offre!!.employeur.codeEntreprise.nomEntreprise + "\n\n\n\n" + "Nom du responsable : " + offre!!.employeur.nomUtilisateur + " " + offre!!.employeur.prénomUtilisateur
            modeEmploi.text = "Mode d'emploie : " + offre!!.modeEmploi.name +  "\n\n\n\n" + "Lieu de l'emploi : " + offre!!.employeur.codeEntreprise.adresseEntreprise
            description.text = "Description du poste : " + offre!!.description +  "\n\n\n\n" + "Nombre de candidatures : " + offre!!.candidatures.count()
        }
        navController = findNavController()

        Modèle.instance.verifierEtudiant { etudiantConnecte ->
            if (etudiantConnecte != null) {
                for (candidature in offre?.candidatures!!) {
                    if (candidature.etudiant.codeUtilisateur == etudiantConnecte.codeUtilisateur) {
                        postulerButton.text = "Postulé"
                        postulerButton.isEnabled = false
                    }else {
                        postulerButton.text = "Postuler"
                        postulerButton.isEnabled = true
                    }
                }
            }
        }

        postulerButton.setOnClickListener {
            val context = view.context

            if (context != null) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Voulez-vous vraiment postuler à l'offre: ${offre?.titrePoste} - ${offre?.employeur?.codeEntreprise?.nomEntreprise}?")

                builder.setPositiveButton("Oui") { _, _ ->
                    postulerButton.text = "Postulé"
                    postulerButton.isEnabled = false

                    offre?.let { it1 -> présentateur.ajouterUneCandidature(it1.idOffre) }
                    val text = "Vous avez postulé à l'offre: ${offre?.titrePoste} - ${offre?.employeur?.codeEntreprise?.nomEntreprise}"
                    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
                }

                builder.setNegativeButton("Non") { _, _ ->
                    val text = "Vous n'avez pas postulé à l'offre: ${offre?.titrePoste} - ${offre?.employeur?.codeEntreprise?.nomEntreprise}"
                    Snackbar.make(view, text, Snackbar.LENGTH_SHORT).show()
                }

                val alertDialog = builder.create()
                alertDialog.setOnShowListener {
                    alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(context.resources.getColor(android.R.color.holo_orange_light))
                    alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(context.resources.getColor(android.R.color.holo_orange_light))
                }

                alertDialog.show()
            }
        }

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_details_offre_to_ecran_recherche)
        }
    }

    companion object {
       var offre: Offre? = null
    }
}