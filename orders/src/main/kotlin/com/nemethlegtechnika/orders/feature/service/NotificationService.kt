package com.nemethlegtechnika.orders.feature.service

import com.nemethlegtechnika.orders.feature.port.Email
import com.nemethlegtechnika.orders.feature.port.NotificationPort
import com.nemethlegtechnika.orders.feature.usecase.NotificationOrder
import com.nemethlegtechnika.orders.feature.usecase.NotificationUseCase
import org.springframework.stereotype.Service

@Service
class NotificationService(
    private val notificationPort: NotificationPort,
): NotificationUseCase {
    override suspend fun notifyNewOrder(order: NotificationOrder) {
        notificationPort.sendEmail(
            Email(
                email = order.owner.email,
                subject = "New Order Received",
                body = generateOrderEmail(order)
            )
        )
    }

    fun generateOrderEmail(order: NotificationOrder): String {
        val tableRows = order.products.joinToString("\n") { product ->
            """
        <tr>
          <td style="padding: 10px; border: 1px solid #ddd;">${product.name}</td>
          <td style="padding: 10px; border: 1px solid #ddd;">${product.description}</td>
          <td style="padding: 10px; border: 1px solid #ddd; text-align: right;">$${product.price}</td>
          <td style="padding: 10px; border: 1px solid #ddd; text-align: center;">${product.quantity}</td>
          <td style="padding: 10px; border: 1px solid #ddd; text-align: right;">$${product.fullPrice}</td>
        </tr>
        """.trimIndent()
        }

        return """
            <!DOCTYPE html>
            <html>
            <head>
              <meta charset="UTF-8">
              <title>Order Notification</title>
            </head>
            <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; padding: 20px;">
              <div style="max-width: 600px; margin: auto; background-color: #ffffff; padding: 20px; border-radius: 8px;">
                <h2 style="text-align: center; color: #333;">🛒 New Order Received</h2>
        
                <p><strong>Customer:</strong> ${order.owner.fullName}<br>
                   <strong>Email:</strong> ${order.owner.email}</p>
        
                <table style="width: 100%; border-collapse: collapse; margin-top: 20px;">
                  <thead>
                    <tr style="background-color: #007BFF; color: white;">
                      <th style="padding: 12px; border: 1px solid #ddd;">Product</th>
                      <th style="padding: 12px; border: 1px solid #ddd;">Description</th>
                      <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">Unit Price</th>
                      <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">Quantity</th>
                      <th style="padding: 12px; border: 1px solid #ddd; text-align: center;">Total</th>
                    </tr>
                  </thead>
                  <tbody>
                    $tableRows
                  </tbody>
                  <tfoot>
                    <tr style="background-color: #f0f0f0;">
                      <td colspan="4" style="padding: 12px; text-align: right; border: 1px solid #ddd;"><strong>Grand Total:</strong></td>
                      <td style="padding: 12px; text-align: right; border: 1px solid #ddd;"><strong>$${order.totalPrice}</strong></td>
                    </tr>
                  </tfoot>
                </table>
        
                <p style="text-align: center; margin-top: 30px; font-size: 0.9em; color: #777;">
                  Thank you for using our service.
                </p>
              </div>
            </body>
            </html>
        """.trimIndent()
    }
}