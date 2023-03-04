package br.ifsp.edu.ads.havagas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.BoringLayout
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import br.ifsp.edu.ads.havagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private val activeMainBinding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(activeMainBinding.root)

        with(activeMainBinding) {
            var itemSelected: Number = 0

            addPhoneCB.setOnClickListener {
                if (phoneBoxLL.visibility == VISIBLE) phoneBoxLL.visibility = GONE
                else phoneBoxLL.visibility = VISIBLE
            }

            trainingSP.onItemSelectedListener = object : OnItemSelectedListener {
                override fun onNothingSelected(p0: AdapterView<*>?) {}

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    itemSelected = position
                    when (position) {
                        1, 2 -> {
                            firstTrainingLL.visibility = VISIBLE
                            secondTrainingLL.visibility = GONE
                            thirdTrainingLL.visibility = GONE
                        }
                        3, 4 -> {
                            firstTrainingLL.visibility = GONE
                            secondTrainingLL.visibility = VISIBLE
                            thirdTrainingLL.visibility = GONE
                        }
                        5, 6 -> {
                            firstTrainingLL.visibility = GONE
                            secondTrainingLL.visibility = GONE
                            thirdTrainingLL.visibility = VISIBLE
                        }
                        else -> {
                            firstTrainingLL.visibility = GONE
                            secondTrainingLL.visibility = GONE
                            thirdTrainingLL.visibility = GONE
                        }
                    }
                }
            }

            clearBT.setOnClickListener {
                fullnameET.text.clear()
                birthdayET.setText("")
                womanRB.isChecked = true
                emailET.text.clear()
                receiveUpdatesCB.isChecked = false
                telephoneET.setText("")
                commercialRB.isChecked = true
                addPhoneCB.isChecked = false
                phoneET.setText("")
                phoneBoxLL.visibility = GONE
                trainingSP.setSelection(0)
                trainingDateFtET.setText("")
                conclusionDateStET.setText("")
                institutionStET.text.clear()
                conclusionDateTtET.setText("")
                institutionTtET.text.clear()
                monographTitleTtET.text.clear()
                supervisorTtET.text.clear()
                jobsET.text.clear()
            }

            saveBT.setOnClickListener {
                var personData = ""
                val fullName: String = fullnameET.text.toString()
                personData = "$personData Nome completo: $fullName,\n"

                val birthDay: String = birthdayET.masked
                personData = "$personData Data de nascimento: $birthDay,\n"

                val womanIsChecked: Boolean = sexRG.checkedRadioButtonId == womanRB.id
                val sex = if (womanIsChecked) "Feminino" else "Masculino"
                personData = "$personData Sexo: $sex,\n"

                val email: String = emailET.text.toString()
                personData = "$personData E-mail: $email,\n"

                val receiveMail: String = if (receiveUpdatesCB.isChecked) "Sim" else "Não"
                personData = "$personData Receber atualizações: $receiveMail,\n"

                val commercialIsChecked: Boolean =
                    telephoneTypeRG.checkedRadioButtonId == commercialRB.id
                val telephone: String = telephoneET.masked
                personData = if (commercialIsChecked)
                    "$personData Telefone comercial: $telephone,\n" else
                    "$personData Telefone residencial: $telephone,\n"

                if (addPhoneCB.isChecked) {
                    val phone: String = phoneET.text.toString()
                    personData = "$personData Telefone celular: $phone,\n"
                }

                when (itemSelected) {
                    1, 2 -> {
                        val training: String = if (itemSelected == 1) "Ensino fundamental" else "Ensino médio"
                        val trainingDate: String = trainingDateFtET.masked
                        personData = "$personData Formação: $training,\n Data de formação: $trainingDate,\n"
                    }
                    3, 4 -> {
                        val training: String = if (itemSelected == 3) "Graduação" else "Especialização"
                        val conclusionDate: String = conclusionDateStET.masked
                        val institution: String = institutionStET.text.toString()
                        personData = "$personData " +
                                "Formação: $training,\n " +
                                "Data de conclusão: $conclusionDate,\n " +
                                "Instituição: $institution,\n"
                    }
                    5, 6 -> {
                        val training: String = if (itemSelected == 5) "Mestrado" else "Doutorado"
                        val conclusionDate: String = conclusionDateTtET.masked
                        val institution: String = institutionTtET.text.toString()
                        val monograph: String = monographTitleTtET.text.toString()
                        val supervisor: String = supervisorTtET.text.toString()
                        personData = "$personData " +
                                "Formação: $training,\n " +
                                "Data de conclusão: $conclusionDate,\n " +
                                "Instituição: $institution,\n " +
                                "Título de monografia: $monograph,\n " +
                                "Orientador: $supervisor,\n"
                    }
                }

                val job: String = jobsET.text.toString()
                personData = "$personData Vaga de interesse: $job,\n"

                Toast.makeText(
                    this@MainActivity,
                    personData,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

