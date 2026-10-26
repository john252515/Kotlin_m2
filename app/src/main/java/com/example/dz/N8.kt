package com.example.dz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

// Цвета из задания
val primaryColor = Color(0xFF2196F3)
val secondaryColor = Color(0xFF1565C0)
val buttonTextColor = Color.White


@Preview(showBackground = true)
@Composable
fun WaterTracker() {
    // Состояние: количество выпитой воды (мл)
    var waterCount by remember { mutableIntStateOf(0) }
    // НОВЫЙ стейт: количество дней подряд с 1500+ мл
    var daysCount by remember { mutableIntStateOf(0) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = "Трекер воды",
            fontSize = 32.sp,
            fontWeight = FontWeight.Bold,
            color = primaryColor
        )
        Spacer(modifier = Modifier.height(48.dp))

        // Вывод текущего количества воды
        Text(
            text = "$waterCount мл",
            fontSize = 48.sp,
            fontWeight = FontWeight.Bold,
            color = secondaryColor
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Вывод количества дней подряд
        Text(
            text = "Дней подряд (1500+ мл): $daysCount",
            fontSize = 18.sp,
            color = secondaryColor
        )
        Spacer(modifier = Modifier.height(32.dp))

        // Кнопка добавления воды
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = primaryColor),
            onClick = { waterCount += 250 }, // ИСПРАВЛЕНО: увеличиваем на 250
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "+250 мл",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = buttonTextColor
            )
        }
        Spacer(modifier = Modifier.height(16.dp))

        // НОВАЯ кнопка «Завершить день»
        OutlinedButton(
            onClick = {
                if (waterCount >= 1500) {
                    daysCount++ // Если выпили 1500+, увеличиваем счётчик дней
                } else {
                    daysCount = 0 // Иначе обнуляем
                }
                waterCount = 0 // Сбрасываем воду
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Завершить день",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = primaryColor
            )
        }
    }
}
