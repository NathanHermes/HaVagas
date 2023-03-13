package br.ifsp.edu.ads.havagas

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.text.BoringLayout
import android.util.Patterns
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.widget.AdapterView
import android.widget.AdapterView.OnItemSelectedListener
import android.widget.Toast
import androidx.core.text.set
import br.ifsp.edu.ads.havagas.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private val activeMainBinding: ActivityMainBinding by lazy {
    ActivityMainBinding.inflate(layoutInflater)
  }

  private var itemSelected: Int = 0
  private var fullName: String? = null
  private var birthday: String? = null
  private var sex: String? = null
  private var email: String? = null
  private var receiveMail: Boolean = false
  private var commercialRadioIsChecked: Boolean = false
  private var telephone: String? = null
  private var addPhoneIsChecked: Boolean = false
  private var phone: String? = null
  private var training: String? = null
  private var trainingDate: String? = null
  private var conclusionDate: String? = null
  private var institution: String? = null
  private var monograph: String? = null
  private var supervisor: String? = null
  private var job: String? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(activeMainBinding.root)

    with(activeMainBinding) {
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
        getDataFromView()
        Toast.makeText(
          this@MainActivity,
          buildToastMessage(),
          Toast.LENGTH_LONG
        ).show()
      }
    }
  }

  override fun onSaveInstanceState(outState: Bundle) {
    super.onSaveInstanceState(outState)
    with(activeMainBinding){
      outState.putString("fullName", fullnameET.text.toString())
      outState.putString("birthday", birthdayET.masked)
      outState.putBoolean("womanRadioIsChecked", womanRB.isChecked)
      outState.putString("email", email)
      outState.putBoolean("receiveMail", receiveUpdatesCB.isChecked)
      outState.putBoolean("commercialRadioIsChecked", commercialRB.isChecked)
      outState.putString("telephone", telephoneET.text.toString())
      outState.putBoolean("addPhone", addPhoneCB.isChecked)
      if (addPhoneCB.isChecked) outState.putString("phone", phoneET.masked)
      outState.putInt("trainingPosition", trainingSP.selectedItemPosition)
      outState.putString("trainingDate", trainingDateFtET.masked)
      outState.putString("conclusionDateSt", conclusionDateStET.masked)
      outState.putString("institutionSt", institutionStET.text.toString())
      outState.putString("conclusionDateTt", conclusionDateTtET.masked)
      outState.putString("institutionTt", institutionTtET.text.toString())
      outState.putString("monograph", monographTitleTtET.text.toString())
      outState.putString("supervisor", supervisorTtET.text.toString())
      outState.putString("job", jobsET.text.toString())
    }
  }

  override fun onRestoreInstanceState(savedInstanceState: Bundle) {
    super.onRestoreInstanceState(savedInstanceState)
    with(activeMainBinding) {
      fullnameET.setText(savedInstanceState.getString("fullName"))
      birthdayET.setText(savedInstanceState.getString("birthday"))
      if (savedInstanceState.getBoolean("womanRadioIsChecked")) womanRB.isChecked = true
      else manRB.isChecked = true
      emailET.setText(savedInstanceState.getString("email"))
      if (savedInstanceState.getBoolean("receiveMail")) receiveUpdatesCB.isChecked = true
      if (savedInstanceState.getBoolean("commercialRadioIsChecked")) commercialRB.isChecked = true
      else homeRB.isChecked= true
      telephoneET.setText(savedInstanceState.getString("telephone"))
      addPhoneCB.isChecked = savedInstanceState.getBoolean("addPhone")
      if (savedInstanceState.getBoolean("addPhone")) {
        phoneBoxLL.visibility = VISIBLE
        phoneET.setText(savedInstanceState.getString(phone))
      }
      trainingSP.setSelection(savedInstanceState.getInt("trainingPosition"))
      trainingDateFtET.setText(savedInstanceState.getString("trainingDate"))
      conclusionDateStET.setText(savedInstanceState.getString("conclusionDateSt"))
      institutionStET.setText(savedInstanceState.getString("institutionSt"))
      conclusionDateTtET.setText(savedInstanceState.getString("conclusionDateTt"))
      institutionTtET.setText(savedInstanceState.getString("institutionTt"))
      monographTitleTtET.setText(savedInstanceState.getString("monograph"))
      supervisorTtET.setText(savedInstanceState.getString("supervisor"))
      jobsET.setText(savedInstanceState.getString("job"))
    }
  }

  private fun buildToastMessage(): String {
    val message = StringBuilder()
    message.append("Nome completo: $fullName,\n")
      .append("Data de nascimento: $birthday,\n")
      .append("Sexo: $sex,\n")
      .append("E-mail: $email,\n")
      .append("Receber atualizações: $receiveMail,\n")

    if (commercialRadioIsChecked) message.append("Telefone comercial: $telephone,\n")
    else message.append("Telefone residencial: $telephone,\n")

    if (addPhoneIsChecked) message.append("Celular: $phone,\n")

    message.append("Formação: $training,\n")
    when (itemSelected) {
      1, 2 -> {
        message.append("Data de formação: $trainingDate,\n")
      }
      3, 4, 5, 6 -> {
        message.append("Data de conclusão: $conclusionDate,\n")
          .append("Instituição: $institution,\n")
        if (itemSelected > 4) message.append("Título de monografia: $monograph,\n")
          .append("Orientador: $supervisor,\n")
      }
    }

    message.append("Vaga de interesse: $job")

    return message.toString()
  }

  private fun getDataFromView() {
    with(activeMainBinding) {
      fullName = fullnameET.text.toString()
      birthday = birthdayET.masked
      sex = if (womanRB.isChecked) "Feminino" else "Masculino"

      email = emailET.text.toString()
      receiveMail = receiveUpdatesCB.isChecked

      commercialRadioIsChecked = telephoneTypeRG.checkedRadioButtonId == commercialRB.id
      telephone = telephoneET.masked

      if (addPhoneCB.isChecked) {
        addPhoneIsChecked = true
        phone = phoneET.text.toString()
      } else {
        addPhoneIsChecked = false
      }

      when (itemSelected) {
        1, 2 -> {
          training = if (itemSelected == 1) "Ensino fundamental" else "Ensino médio"
          trainingDate = trainingDateFtET.masked
        }
        3, 4 -> {
          training = if (itemSelected == 3) "Graduação" else "Especialização"
          conclusionDate = conclusionDateStET.masked
          institution = institutionStET.text.toString()
        }
        5, 6 -> {
          training = if (itemSelected == 5) "Mestrado" else "Doutorado"
          conclusionDate = conclusionDateTtET.masked
          institution = institutionTtET.text.toString()
          monograph = monographTitleTtET.text.toString()
          supervisor = supervisorTtET.text.toString()
        }
      }

      job = jobsET.text.toString()
    }
  }
}
