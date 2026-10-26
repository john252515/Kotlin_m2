package com.example.dz

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlin.random.Random

// 1. Модели данных (объявлены ТОЛЬКО здесь, чтобы не было Redeclaration)
data class Cart(
    val products: List<Product>
)

data class Product(
    val id: Int,
    val name: String,
    val price: Int
)

// 2. MainActivity (должна быть ТОЛЬКО в одном файле проекта!)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ShoppingCartScreen()
        }
    }
}

// 3. Исправленный экран корзины
@Composable
fun ShoppingCartScreen() {
    var products by remember {
        mutableStateOf(
            listOf(
                Product(0, "Товар #1", 100),
                Product(1, "Товар #2", 150),
                Product(2, "Товар #3", 56)
            )
        )
    }

    // Вычисляем сумму и размер на основе состояния (без мутации в цикле!)
    val totalSum = products.sumOf { it.price }
    val productSize = products.size

    val context = LocalContext.current

    // Показываем Toast через LaunchedEffect (исправление побочного эффекта)
    if (totalSum > 500) {
        LaunchedEffect(totalSum) {
            Toast.makeText(context, "Доставка бесплатная!", Toast.LENGTH_SHORT).show()
        }
    }

    Column {
        for (product in products) {
            Text(text = "${product.name} - ${product.price} рублей")
        }

        Spacer(modifier = Modifier.height(10.dp))
        Text(text = "Товаров на сумму: $totalSum рублей")

        AddProductSection(
            onAdd = {
                products = products + Product(
                    id = products.size,
                    name = "Товар #${products.size + 1}",
                    price = Random.nextInt(1, 100)
                )
            }
        )

        RemoveProductSection(
            productSize = productSize,
            onRemove = {
                if (products.isNotEmpty()) {
                    products = products.dropLast(1)
                }
            }
        )
    }
}

// 4. Отдельные Composable-функции (идемпотентные)
@Composable
fun AddProductSection(onAdd: () -> Unit) {
    Button(onClick = onAdd) {
        Text(text = "Добавить товар")
    }
}

@Composable
fun RemoveProductSection(productSize: Int, onRemove: () -> Unit) {
    if (productSize > 0) {
        Button(onClick = onRemove) {
            Text(text = "Удалить товар")
        }
    }
}