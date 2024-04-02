package crosemont.dti.g55.application_gestion_de_stage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import crosemont.dti.g55.application_gestion_de_stage.présentation.vue.OffresSauvegardeesAdaptateur

class ecran_offres_sauvegardees : Fragment() {

    private lateinit var btnAccueil: ImageButton
    private lateinit var offresSauvegardéesRecyclerView: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(
            R.layout.fragment_ecran_offres_sauvegardees,
            container,
            false
        )
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAccueil = view.findViewById(R.id.btn_accueil)

        offresSauvegardéesRecyclerView = view.findViewById(R.id.offreSauvegardéesRecycler)

        val navController = findNavController()

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_ecran_offres_sauvegardees_to_ecran_recherche)
        }
    }
}