package crosemont.dti.g55.application_gestion_de_stage

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import crosemont.dti.g55.application_gestion_de_stage.domaine.Offre
import crosemont.dti.g55.application_gestion_de_stage.présentation.présentateurs.OffrePrésentateur
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.CandidatureClickListener
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.SauvegardeClickListner
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.offreAdaptateur

class ecran_recherche : Fragment(), CandidatureClickListener {

    private lateinit var btn_profil: ImageButton
    private lateinit var btnCandidatures: ImageButton
    private lateinit var btnPréférences: ImageButton
    private lateinit var btnStage: ImageButton
    private lateinit var recyclerView: RecyclerView
    private lateinit var offreAdapter: offreAdaptateur
    private lateinit var presentateur_offre: OffrePrésentateur
    private lateinit var edit_recherche: EditText
    private lateinit var loadingView: View
    private lateinit var loadingProgressBar: ProgressBar
    private lateinit var navController: NavController
    private lateinit var filtre_sauvegarde: ImageButton
    private lateinit var rechercheText : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_ecran_recherche, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        edit_recherche = view.findViewById(R.id.edit_recherche)
        filtre_sauvegarde = view.findViewById(R.id.btn_favoris_filtre)
        btnCandidatures = view.findViewById(R.id.btn_candidatures)
        btnPréférences = view.findViewById(R.id.btn_preferences)
        btnStage = view.findViewById(R.id.btn_stage)
        loadingView = view.findViewById(R.id.loadingView)
        loadingProgressBar = view.findViewById(R.id.loadingProgressBar)
        recyclerView = view.findViewById(R.id.offresRecycler)
        loadingView = view.findViewById(R.id.loadingView)
        rechercheText = view.findViewById(R.id.rechercheText)
        offreAdapter = offreAdaptateur(emptyList(), this)
        presentateur_offre = OffrePrésentateur(this)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = offreAdapter

        btn_profil = view.findViewById(R.id.btn_profil)

        navController = findNavController()

        btn_profil.setOnClickListener {
            navController.navigate(R.id.action_ecran_recherche_to_ecran_profil_etudiant)
        }

        presentateur_offre.chargerToutesLesOffres()

        edit_recherche.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                val motRecherche = s.toString()
                presentateur_offre.filtrerOffres(motRecherche)
            }
        })


        btnCandidatures.setOnClickListener {
            navController.navigate(R.id.action_ecran_recherche_to_ecran_candidatures_et_offres_sauvegardees)
        }

        btnPréférences.setOnClickListener {
            navController.navigate(R.id.action_ecran_recherche_to_ecran_parametres)
        }

        btnStage.setOnClickListener {
            navController.navigate(R.id.action_ecran_recherche_to_ecran_stage)
        }

    }

    override fun onPostulerClick() {
        navController.navigate(R.id.action_ecran_recherche_to_details_offre)
    }

    fun afficherOffres(offres: List<Offre>){
        offreAdapter.setOffres(offres)
        offreAdapter.notifyDataSetChanged()
    }

    fun afficherErreur(message: String) {
        Snackbar.make(requireView(), message, Snackbar.LENGTH_SHORT).show()
    }

    fun afficherEcranChargement(est_affiché:Boolean){
        loadingView.visibility = if(est_affiché) View.VISIBLE else View.GONE
        loadingProgressBar.visibility = if (est_affiché) View.VISIBLE else View.GONE
    }

}