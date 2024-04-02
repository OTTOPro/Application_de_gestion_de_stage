package crosemont.dti.g55.application_gestion_de_stage

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import crosemont.dti.g55.application_gestion_de_stage.domaine.Candidature
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.CandidaturePrésentateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.CandidatureAdaptateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.SuppressionClickListener


class ecran_candidatures_et_offres_sauvegardees : Fragment(), SuppressionClickListener {

    private lateinit var btnProfil: ImageButton
    private lateinit var btnAccueil: ImageButton
    private lateinit var btnPréférences: ImageButton
    private lateinit var btnStage: ImageButton
    private lateinit var candidatureRecyclerView: RecyclerView
    private lateinit var candidatureAdaptateur: CandidatureAdaptateur
    private lateinit var candidaturePrésentateur: CandidaturePrésentateur
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_ecran_candidatures_et_offres_sauvegardees,
            container,
            false
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAccueil = view.findViewById(R.id.btn_accueil)
        btnProfil = view.findViewById(R.id.btn_profil)
        btnPréférences = view.findViewById(R.id.btn_preferences)
        btnStage = view.findViewById(R.id.btn_stage)
        candidatureRecyclerView = view.findViewById(R.id.candidaturesRecycler)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        candidaturePrésentateur = CandidaturePrésentateur(this)
        candidatureAdaptateur = CandidatureAdaptateur(emptyList(), this)
        candidaturePrésentateur.obtenirCandidatures()
        candidatureRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        candidatureRecyclerView.adapter = candidatureAdaptateur


        val navController = findNavController()

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_ecran_candidatures_et_offres_sauvegardees_to_ecran_recherche)
        }

        btnProfil.setOnClickListener {
            navController.navigate(R.id.action_ecran_candidatures_et_offres_sauvegardees_to_ecran_profil_etudiant)
        }

        btnPréférences.setOnClickListener {
            navController.navigate(R.id.action_ecran_candidatures_et_offres_sauvegardees_to_ecran_parametres)
        }

        btnStage.setOnClickListener {
            navController.navigate(R.id.action_ecran_candidatures_et_offres_sauvegardees_to_ecran_stage)
        }
    }

    fun afficherCandidatures(candidatures: List<Candidature>) {
        candidatureAdaptateur.setCandidatures(candidatures)
    }

    fun afficherErreur(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    fun afficherEcranChargement(est_affiché:Boolean){
        loadingProgressBar.visibility = if (est_affiché) View.VISIBLE else View.GONE
    }

    override fun onSuppressionClick(code_candidature: Int) {

        val context = view?.context

        if (context != null) {
            val builder = AlertDialog.Builder(context)
            builder.setTitle("Confirmation")
            builder.setMessage("Voulez-vous vraiment supprimer cette candidature ?")

            builder.setPositiveButton("Oui") { _, _ ->
                candidaturePrésentateur.supprimerLaCandidature(code_candidature)
                val text = "Vous avez supprimer la candidature"
                view?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }
            }

            builder.setNegativeButton("Non") { _, _ ->
                val text = "Vous n'avez pas supprimer la candidature"
                view?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }
            }

            val alertDialog = builder.create()
            alertDialog.setOnShowListener {
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE)?.setTextColor(context.resources.getColor(android.R.color.holo_orange_light))
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE)?.setTextColor(context.resources.getColor(android.R.color.holo_orange_light))
            }

            alertDialog.show()
        }
    }

}