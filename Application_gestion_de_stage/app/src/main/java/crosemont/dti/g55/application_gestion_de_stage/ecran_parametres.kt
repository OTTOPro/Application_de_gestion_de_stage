package crosemont.dti.g55.application_gestion_de_stage

import android.app.AlertDialog
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.Switch
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar

private const val DARK_MODE_PREF = "dark_mode_pref"
private const val COLOR_PREF = "color_pref"


class ecran_parametres : Fragment() {

    private lateinit var btnProfil: ImageButton
    private lateinit var btnAccueil: ImageButton
    private lateinit var btnCandidatures: ImageButton
    private lateinit var btnStage: ImageButton
    private lateinit var basculerModeSombre: Switch
    private lateinit var btnDéconnexion: Button
    private lateinit var sharedPrefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ecran_parametres, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        btnAccueil = view.findViewById(R.id.btn_accueil)
        btnProfil = view.findViewById(R.id.btn_profil)
        btnCandidatures = view.findViewById(R.id.btn_candidatures)
        btnStage = view.findViewById(R.id.btn_stage)
        basculerModeSombre = view.findViewById(R.id.basculerModeSombre)
        btnDéconnexion = view.findViewById(R.id.btn_déconnexion)

        val navController = findNavController()

        btnAccueil.setOnClickListener {
            navController.navigate(R.id.action_ecran_parametres_to_ecran_recherche)
        }

        btnProfil.setOnClickListener {
            navController.navigate(R.id.action_ecran_parametres_to_ecran_profil_etudiant)
        }

        btnCandidatures.setOnClickListener {
            navController.navigate(R.id.action_ecran_parametres_to_ecran_candidatures_et_offres_sauvegardees)
        }

        btnStage.setOnClickListener {
            navController.navigate(R.id.action_ecran_parametres_to_ecran_stage)
        }

        btnDéconnexion.setOnClickListener {

            val context = view?.context

            if (context != null) {
                val builder = AlertDialog.Builder(context)
                builder.setTitle("Confirmation")
                builder.setMessage("Voulez-vous vraiment se deconnecter ?")

                builder.setPositiveButton("Oui") { _, _ ->
                    navController.navigate(R.id.action_ecran_parametres_to_ecran_accueil)
                    val text = "Vous etes deconnecté"
                    view?.let { Snackbar.make(it, text, Snackbar.LENGTH_SHORT).show() }
                }

                builder.setNegativeButton("Non") { _, _ ->
                    val text = "Vous n'etes pas deconnecté"
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

        sharedPrefs = requireActivity().getPreferences(Context.MODE_PRIVATE)

        val isDarkModeEnabled = sharedPrefs.getBoolean(DARK_MODE_PREF, false)

        basculerModeSombre.isChecked = isDarkModeEnabled

        basculerModeSombre.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                Log.d("DarkMode", "Enabled")
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                Log.d("DarkMode", "Disabled")
            }

            with(sharedPrefs.edit()) {
                putBoolean(DARK_MODE_PREF, isChecked)
                putInt(COLOR_PREF, Color.BLACK)
                apply()
            }

            requireActivity().recreate()
        }
    }

}