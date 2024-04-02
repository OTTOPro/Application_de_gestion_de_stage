package crosemont.dti.g55.application_gestion_de_stage

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
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.domaine.Stage
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.EtudiantPrésentateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.StagePrésentateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.EtudiantAdaptateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.StageAdaptateur

class ecran_stage : Fragment() {

    private lateinit var btnProfil: ImageButton
    private lateinit var btnAccueil: ImageButton
    private lateinit var btnCandidatures: ImageButton
    private lateinit var btnPréférences: ImageButton
    private lateinit var stageRecyclerView: RecyclerView
    private lateinit var stageAdaptateur: StageAdaptateur
    private lateinit var stagePrésentateur: StagePrésentateur
    private lateinit var loadingView: View
    private lateinit var loadingProgressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ecran_stage, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAccueil = view.findViewById(R.id.btn_accueil)
        btnProfil = view.findViewById(R.id.btn_profil)
        btnCandidatures = view.findViewById(R.id.btn_candidatures)
        btnPréférences = view.findViewById(R.id.btn_preferences)
        loadingView = view.findViewById(R.id.loadingView)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        stageRecyclerView = view.findViewById(R.id.stageRecycler)
        stageAdaptateur = StageAdaptateur(emptyList())
        stagePrésentateur = StagePrésentateur(this)
        stageRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        stageRecyclerView.adapter = stageAdaptateur
        stagePrésentateur.chargerStage()

        val navController = findNavController()

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_ecran_stage_to_ecran_recherche)
        }

        btnProfil.setOnClickListener {
            navController.navigate(R.id.action_ecran_stage_to_ecran_profil_etudiant)
        }

        btnCandidatures.setOnClickListener {
            navController.navigate(R.id.action_ecran_stage_to_ecran_candidatures_et_offres_sauvegardees)
        }

        btnPréférences.setOnClickListener {
            navController.navigate(R.id.action_ecran_stage_to_ecran_parametres)
        }
    }

    fun afficherStage(stage: List<Stage>){
        stageAdaptateur.setStage(stage)
    }

    fun afficherErreur(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    fun afficherEcranChargement(est_affiché:Boolean){
        loadingView.visibility = if(est_affiché) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (est_affiché) View.VISIBLE else View.GONE
    }
}