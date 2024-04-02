package crosemont.dti.g55.application_gestion_de_stage

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import crosemont.dti.g55.application_gestion_de_stage.domaine.Etudiant
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.EtudiantPrésentateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.EtudiantAdaptateur

class ecran_profil_etudiant : Fragment() {

    private lateinit var btnAccueil: ImageButton
    private lateinit var btnCandidatures: ImageButton
    private lateinit var btnPréférences: ImageButton
    private lateinit var btnStage: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var etudiantAdaptateur: EtudiantAdaptateur
    private lateinit var etudiantPrésentateur: EtudiantPrésentateur
    private lateinit var loadingProgressBar: ProgressBar

    private val startForResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val data: Intent? = result.data
                if (data != null) {
                    val selectedImageUri: Uri? = data.data
                    etudiantAdaptateur.setImageUri(selectedImageUri)

                    val extras = data.extras
                    if (extras != null && extras.containsKey("data")) {
                        val imageBitmap = extras.get("data") as Bitmap
                        etudiantAdaptateur.setImageBitmap(imageBitmap)
                    }
                }
            }
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ecran_profil_etudiant, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAccueil = view.findViewById(R.id.btn_accueil)
        btnCandidatures = view.findViewById(R.id.btn_candidatures)
        btnPréférences = view.findViewById(R.id.btn_preferences)
        btnStage = view.findViewById(R.id.btn_stage)
        recyclerView = view.findViewById(R.id.profilRecycler)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        etudiantAdaptateur = EtudiantAdaptateur(emptyList(), startForResult)
        etudiantPrésentateur = EtudiantPrésentateur(this)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = etudiantAdaptateur
        etudiantPrésentateur.chargerAvater()
        etudiantPrésentateur.chargerEtudiant()

        val navController = findNavController()

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_ecran_profil_etudiant_to_ecran_recherche)
        }

        btnCandidatures.setOnClickListener {
            navController.navigate(R.id.action_ecran_profil_etudiant_to_ecran_candidatures_et_offres_sauvegardees)
        }

        btnPréférences.setOnClickListener {
            navController.navigate(R.id.action_ecran_profil_etudiant_to_ecran_parametres)
        }

        btnStage.setOnClickListener {
            navController.navigate(R.id.action_ecran_profil_etudiant_to_ecran_stage)
        }
    }

    fun afficherEtudiant(etudiant: List<Etudiant>){
        etudiantAdaptateur.setEtudiants(etudiant)
    }

    fun afficherAvatar(avatarUrl: String){
        etudiantAdaptateur.setAvatar(avatarUrl)
    }

    fun afficherErreur(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    fun afficherEcranChargement(est_affiché:Boolean){
        loadingProgressBar.visibility = if (est_affiché) View.VISIBLE else View.GONE
    }

}