package com.kothias.clinic

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class QuestionnaireActivity : AppCompatActivity() {

    private lateinit var apiService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_questionnaire)

        // Initialize ApiService using RetrofitClient
        apiService = RetrofitClient.getInstance().create(ApiService::class.java)

        // Find all the views
        val editTextName: EditText = findViewById(R.id.editTextName)
        val editTextAge: EditText = findViewById(R.id.editTextAge)
        val radioGroupGender: RadioGroup = findViewById(R.id.radioGroupGender)
        val radioGroupWeight: RadioGroup = findViewById(R.id.radioGroupWeight)
        val radioGroupWorking: RadioGroup = findViewById(R.id.radioGroupWorking)
        val radioGroupWorkShift: RadioGroup = findViewById(R.id.radioGroupWorking)
        val radioGroupJobNature: RadioGroup = findViewById(R.id.radioGroupNatureOfJob)
        val radioGroupLiftHeavyWeights: RadioGroup = findViewById(R.id.radioGroupLiftingWeights)
        val radioGroupUseDevicesOften: RadioGroup = findViewById(R.id.radioGroupCorporateJob)
        val radioGroupSittingHours: RadioGroup = findViewById(R.id.radioGroupHoursSitting)
        val radioGroupFastFoodFrequency: RadioGroup = findViewById(R.id.radioGroupOutsideFood)
        val radioGroupConsumeOilySpicyFood: RadioGroup = findViewById(R.id.radioGroupOilySpicyFood)
        val radioGroupDinnerTime: RadioGroup = findViewById(R.id.radioGroupDinnerTime)
        val radioGroupDigestionProblems: RadioGroup = findViewById(R.id.radioGroupDigestionProblems)
        val radioGroupStoolFrequency: RadioGroup = findViewById(R.id.radioGroupTypeOfConstipation)
        val radioGroupButtockPain: RadioGroup = findViewById(R.id.radioGroupPainWhilePassingStool)
        val radioGroupCuttingPain: RadioGroup = findViewById(R.id.radioGroupCuttingPain)
        val radioGroupBurningSensation: RadioGroup = findViewById(R.id.radioGroupBurningPain)
        val radioGroupItching: RadioGroup = findViewById(R.id.radioGroupItchingPain)
        val radioGroupPileMassComingOut: RadioGroup = findViewById(R.id.radioGroupPileMassComingOut)
        val radioGroupPileMassCondition: RadioGroup = findViewById(R.id.radioGroupPileMassCondition)
        val radioGroupBleedingIssue: RadioGroup = findViewById(R.id.radioGroupBleedingIssue)
        val radioGroupBleedingType: RadioGroup = findViewById(R.id.radioGroupBleedingType)
        val radioGroupBleedingAmount: RadioGroup = findViewById(R.id.radioGroupAmountOfBleeding)
        val radioGroupBloodColor: RadioGroup = findViewById(R.id.radioGroupColorOfBlood)
        val radioGroupPusDischarge: RadioGroup = findViewById(R.id.radioGroupPusDischarge)
        val radioGroupBoilsAroundAnus: RadioGroup = findViewById(R.id.radioGroupBoilsAroundAnus)
        val radioGroupAnusSwelling: RadioGroup = findViewById(R.id.radioGroupSwellingInArea)
        val radioGroupFirstTimeSwelling: RadioGroup = findViewById(R.id.radioGroupSwellingOccurrence)
        val radioGroupBodyHairType: RadioGroup = findViewById(R.id.radioGroupKindOfBodyHair)
        val radioGroupDiabetes: RadioGroup = findViewById(R.id.radioGroupSufferingFromDiabetes)
        val radioGroupRednessSwelling: RadioGroup = findViewById(R.id.radioGroupRednessOrSwelling)
        val radioGroupTreatmentType: RadioGroup = findViewById(R.id.radioGroupUndergoneTreatment)
        val radioGroupTreatmentKind: RadioGroup = findViewById(R.id.radioGroupTypeOfTreatment)
        val radioGroupBloodColorDuringTreatment: RadioGroup = findViewById(R.id.radioGroupBloodColorDuringTreatment)
        val radioGroupRecurrence: RadioGroup = findViewById(R.id.radioGroupRecurrenceOfPiloNidal)
        val radioGroupRelapseFrequency: RadioGroup = findViewById(R.id.radioGroupRelapseInThisDisease)
        val radioGroupPainWhileSitting: RadioGroup = findViewById(R.id.radioGroupPainWhileSitting)
        val radioGroupPainWhileDefecation: RadioGroup = findViewById(R.id.radioGroupPainWhileDefecation)
        val radioGroupWeightLoss: RadioGroup = findViewById(R.id.radioGroupLosingWeight)
        val radioGroupJustDeliveredBaby: RadioGroup = findViewById(R.id.radioGroupJustDeliveredABaby)
        val radioGroupFamilyAnalCancer: RadioGroup = findViewById(R.id.radioGroupFamilyHistoryOfCancer)
        val radioGroupFirstTimeDoctorVisit: RadioGroup = findViewById(R.id.radioGroupFirstTimeDoctorVisit)
        val buttonSubmit: Button = findViewById(R.id.submit_button)

        // Set an OnClickListener for the Submit button
        buttonSubmit.setOnClickListener {
            val name = findViewById<EditText>(R.id.editTextName)?.text?.toString() ?: ""
            val age = findViewById<EditText>(R.id.editTextAge)?.text?.toString() ?: ""
            val gender = findViewById<RadioButton>(radioGroupGender.checkedRadioButtonId)?.text?.toString() ?: ""
            val weight = findViewById<RadioButton>(radioGroupWeight.checkedRadioButtonId)?.text?.toString() ?: ""
            val working = findViewById<RadioButton>(radioGroupWorking.checkedRadioButtonId)?.text?.toString() ?: ""
            val work_shift = findViewById<RadioButton>(radioGroupWorkShift.checkedRadioButtonId)?.text?.toString() ?: ""
            val job_nature = findViewById<RadioButton>(radioGroupJobNature.checkedRadioButtonId)?.text?.toString() ?: ""
            val lift_heavy_weights = findViewById<RadioButton>(radioGroupLiftHeavyWeights.checkedRadioButtonId)?.text?.toString() ?: ""
            val use_devices_often = findViewById<RadioButton>(radioGroupUseDevicesOften.checkedRadioButtonId)?.text?.toString() ?: ""
            val sitting_hours = findViewById<RadioButton>(radioGroupSittingHours.checkedRadioButtonId)?.text?.toString() ?: ""
            val fast_food_frequency = findViewById<RadioButton>(radioGroupFastFoodFrequency.checkedRadioButtonId)?.text?.toString() ?: ""
            val consume_oily_spicy_food = findViewById<RadioButton>(radioGroupConsumeOilySpicyFood.checkedRadioButtonId)?.text?.toString() ?: ""
            val dinner_time = findViewById<RadioButton>(radioGroupDinnerTime.checkedRadioButtonId)?.text?.toString() ?: ""
            val digestion_problems = findViewById<RadioButton>(radioGroupDigestionProblems.checkedRadioButtonId)?.text?.toString() ?: ""
            val stool_frequency = findViewById<RadioButton>(radioGroupStoolFrequency.checkedRadioButtonId)?.text?.toString() ?: ""
            val buttock_pain = findViewById<RadioButton>(radioGroupButtockPain.checkedRadioButtonId)?.text?.toString() ?: ""
            val cutting_pain = findViewById<RadioButton>(radioGroupCuttingPain.checkedRadioButtonId)?.text?.toString() ?: ""
            val burning_sensation = findViewById<RadioButton>(radioGroupBurningSensation.checkedRadioButtonId)?.text?.toString() ?: ""
            val itching = findViewById<RadioButton>(radioGroupItching.checkedRadioButtonId)?.text?.toString() ?: ""
            val pile_mass_coming_out = findViewById<RadioButton>(radioGroupPileMassComingOut.checkedRadioButtonId)?.text?.toString() ?: ""
            val pile_mass_condition = findViewById<RadioButton>(radioGroupPileMassCondition.checkedRadioButtonId)?.text?.toString() ?: ""
            val bleeding_issue = findViewById<RadioButton>(radioGroupBleedingIssue.checkedRadioButtonId)?.text?.toString() ?: ""
            val bleeding_type = findViewById<RadioButton>(radioGroupBleedingType.checkedRadioButtonId)?.text?.toString() ?: ""
            val bleeding_amount = findViewById<RadioButton>(radioGroupBleedingAmount.checkedRadioButtonId)?.text?.toString() ?: ""
            val blood_color = findViewById<RadioButton>(radioGroupBloodColor.checkedRadioButtonId)?.text?.toString() ?: ""
            val pus_discharge = findViewById<RadioButton>(radioGroupPusDischarge.checkedRadioButtonId)?.text?.toString() ?: ""
            val boils_around_anus = findViewById<RadioButton>(radioGroupBoilsAroundAnus.checkedRadioButtonId)?.text?.toString() ?: ""
            val anus_swelling = findViewById<RadioButton>(radioGroupAnusSwelling.checkedRadioButtonId)?.text?.toString() ?: ""
            val first_time_swelling = findViewById<RadioButton>(radioGroupFirstTimeSwelling.checkedRadioButtonId)?.text?.toString() ?: ""
            val body_hair_type = findViewById<RadioButton>(radioGroupBodyHairType.checkedRadioButtonId)?.text?.toString() ?: ""
            val diabetes = findViewById<RadioButton>(radioGroupDiabetes.checkedRadioButtonId)?.text?.toString() ?: ""
            val redness_swelling = findViewById<RadioButton>(radioGroupRednessSwelling.checkedRadioButtonId)?.text?.toString() ?: ""
            val treatment_type = findViewById<RadioButton>(radioGroupTreatmentType.checkedRadioButtonId)?.text?.toString() ?: ""
            val treatment_kind = findViewById<RadioButton>(radioGroupTreatmentKind.checkedRadioButtonId)?.text?.toString() ?: ""
            val blood_color_during_treatment = findViewById<RadioButton>(radioGroupBloodColorDuringTreatment.checkedRadioButtonId)?.text?.toString() ?: ""
            val recurrence = findViewById<RadioButton>(radioGroupRecurrence.checkedRadioButtonId)?.text?.toString() ?: ""
            val relapse_frequency = findViewById<RadioButton>(radioGroupRelapseFrequency.checkedRadioButtonId)?.text?.toString() ?: ""
            val pain_while_sitting = findViewById<RadioButton>(radioGroupPainWhileSitting.checkedRadioButtonId)?.text?.toString() ?: ""
            val pain_while_defecation = findViewById<RadioButton>(radioGroupPainWhileDefecation.checkedRadioButtonId)?.text?.toString() ?: ""
            val weight_loss = findViewById<RadioButton>(radioGroupWeightLoss.checkedRadioButtonId)?.text?.toString() ?: ""
            val just_delivered_baby = findViewById<RadioButton>(radioGroupJustDeliveredBaby.checkedRadioButtonId)?.text?.toString() ?: ""
            val family_anal_cancer = findViewById<RadioButton>(radioGroupFamilyAnalCancer.checkedRadioButtonId)?.text?.toString() ?: ""
            val first_time_doctor_visit = findViewById<RadioButton>(radioGroupFirstTimeDoctorVisit.checkedRadioButtonId)?.text?.toString() ?: ""

            // Create a Questionnaire object
            val questionnaire = Questionnaire(
                name,
                age,
                gender,
                weight,
                working,
                work_shift,
                job_nature,
                lift_heavy_weights,
                use_devices_often,
                sitting_hours,
                fast_food_frequency,
                consume_oily_spicy_food,
                dinner_time,
                digestion_problems,
                stool_frequency,
                buttock_pain,
                cutting_pain,
                burning_sensation,
                itching,
                pile_mass_coming_out,
                pile_mass_condition,
                bleeding_issue,
                bleeding_type,
                bleeding_amount,
                blood_color,
                pus_discharge,
                boils_around_anus,
                anus_swelling,
                first_time_swelling,
                body_hair_type,
                diabetes,
                redness_swelling,
                treatment_type,
                treatment_kind,
                blood_color_during_treatment,
                recurrence,
                relapse_frequency,
                pain_while_sitting,
                pain_while_defecation,
                weight_loss,
                just_delivered_baby,
                family_anal_cancer,
                first_time_doctor_visit
            )

            // Log the JSON payload being sent
            val gson = Gson()
            val requestBody = gson.toJson(questionnaire)
            Log.d("QuestionnaireActivity", "Request Payload: $requestBody")

            // Show a progress message
            Toast.makeText(this, "Submitting your responses, please wait...", Toast.LENGTH_SHORT).show()
            // Send email notification after 50 seconds
            Handler(Looper.getMainLooper()).postDelayed({
                sendEmailNotification()
            }, 50000) // 50 seconds delay

            // Call API to submit questionnaire
            apiService.submitQuestionnaire(questionnaire).enqueue(object : Callback<ResponseData> {
                // Handle API response for submitting questionnaire
                override fun onResponse(call: Call<ResponseData>, response: Response<ResponseData>) {
                    if (response.isSuccessful) {
                        // Handle successful response from API
                        val responseData = response.body()
                        // Check if response data is not null
                        responseData?.let {
                            Toast.makeText(this@QuestionnaireActivity, "Your responses have been submitted successfully! Redirecting to prediction details...", Toast.LENGTH_SHORT).show()
                            Log.d("QuestionnaireActivity", "Response: ${it.result}")

                            // Call the predict API
                            getPrediction()
                        }
                    } else {
                        Toast.makeText(this@QuestionnaireActivity, "Please try again after some time.", Toast.LENGTH_SHORT).show()
                        Log.d("QuestionnaireActivity", "Failed to submit questionnaire: ${response.code()} ${response.message()}")
                    }
                }

                override fun onFailure(call: Call<ResponseData>, t: Throwable) {
                    Toast.makeText(this@QuestionnaireActivity, "Please wait a moment while we set up the server for you. Try again in a minute.", Toast.LENGTH_SHORT).show()
                    Log.d("QuestionnaireActivity", "Error submitting questionnaire", t)
                }
            })
        }
    }

    // Function to get prediction from the API
    private fun getPrediction() {
        // Call the API to get prediction
        apiService.getPrediction().enqueue(object : Callback<PredictionResponse> {
            // Handle API response for getting prediction
            override fun onResponse(call: Call<PredictionResponse>, response: Response<PredictionResponse>) {
                if (response.isSuccessful) {
                    // Handle successful response from API
                    val predictionResponse = response.body()
                    // Check if prediction response is not null
                    predictionResponse?.let {
                        // Assuming prediction is a list, display the first item
                        val predictionText = it.prediction.firstOrNull() ?: "No prediction available"

                        // Create an Intent to start PredictionResponseActivity
                        val intent = Intent(this@QuestionnaireActivity, PredictionResponseActivity::class.java)
                        intent.putExtra("PREDICTION_TEXT", predictionText)

                        // Start PredictionResponseActivity
                        startActivity(intent)
                    }
                } else {
                    Toast.makeText(this@QuestionnaireActivity, "Failed to get prediction. Please fill out all required fields before submitting.", Toast.LENGTH_SHORT).show()
                    Log.d("QuestionnaireActivity", "Failed to get prediction: ${response.code()} ${response.message()}")
                }
            }

            override fun onFailure(call: Call<PredictionResponse>, t: Throwable) {
                Toast.makeText(this@QuestionnaireActivity, "Error getting prediction.", Toast.LENGTH_SHORT).show()
                Log.d("QuestionnaireActivity", "Error getting prediction", t)
            }
        })
    }

    // Function to get the current user's email
    private fun getCurrentUserEmail(): String? {
        val currentUser = FirebaseAuth.getInstance().currentUser
        return currentUser?.email
    }

    // Function to send an email notification to the user
    private fun sendEmailNotification() {
        val userEmail = getCurrentUserEmail()

        if (userEmail != null) {
            val subject = "Thank you for your submission!"
            val content = """
            <h1>Dear Patient,</h1>
            <p>Great news! Your previous submission is now processed, and you can enter your details again to receive your prediction.</p>
            <p>Simply visit our application and submit your details to get the latest prediction results.</p>
            <p>If you have any questions or need further assistance, feel free to reach out to us.</p>
            <p>Best regards,<br>Dr. Kothia's Clinic</p>
        """
            SendinblueHelper.sendEmail(
                to = userEmail, // Use the logged-in user's email
                subject = subject,
                content = content
            ) { success, message ->
                if (success) {
                    Log.d("SendinblueHelper", "Email sent successfully")
                } else {
                    Log.d("SendinblueHelper", "Failed to send email: $message")
                }
            }
        } else {
            Log.e("SendinblueHelper", "Failed to retrieve user email")
        }
    }
}