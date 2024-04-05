package com.example.piwatch.presentation.components

import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.widget.RatingBar
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import coil.size.Size
import com.example.piwatch.R
import com.example.piwatch.data.remote.TMDBService
import com.example.piwatch.domain.model.Movie
import com.example.piwatch.ui.theme.PiWatchTheme
import kotlin.math.ceil
import kotlin.math.floor

@Composable
fun TextComponent(
    text: String,
    color: Color = MaterialTheme.colorScheme.onBackground
) {
    Text(
        text = text,
        modifier = Modifier,
        style = MaterialTheme.typography.bodyLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        color = color
    )
}

@Composable
fun ContentTextComponent(
    text: String,
    modifier: Modifier = Modifier
) {
        Text(
            text = text,
            modifier = modifier,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
}
@Composable
fun ParagraphContentTextComponent(
    text: String,
    modifier: Modifier = Modifier
) {
        Text(
            text = text,
            modifier = modifier,
            style = TextStyle(
                fontSize = 10.sp,
                fontWeight = FontWeight.Light
            ),
        )
}



@Composable
fun HeadingTextComponent(
    text: String,
    modifier: Modifier = Modifier,
    textAlign: TextAlign = TextAlign.Start
) {
    Text(
        text = text,
        modifier = modifier,
        style = MaterialTheme.typography.titleLarge,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        textAlign = textAlign
    )
}
@Composable
fun TitleTextComponent(text: String) {
    Text(
        text = text,
        modifier = Modifier,
        style = MaterialTheme.typography.displaySmall,
        maxLines = 2,
    )
}

@Composable
fun SecondaryTextComponent(
    modifier: Modifier = Modifier,
    text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth(),
        style = MaterialTheme.typography.titleMedium,
        color = MaterialTheme.colorScheme.secondary
    )
}

@Composable
fun Tag(
    text: String,
    navigateToTag: () -> Unit
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable { navigateToTag }
            .background(MaterialTheme.colorScheme.secondary)
    ){
        Text(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSecondary,
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
fun primaryButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    color: Color,
    icon: ImageVector
){
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(10.dp))
            .clickable {
                onClick()
            }
            .background(color)
    ){
        Row(
            modifier = modifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start
        ) {
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
                modifier = modifier.padding(10.dp)
            )
        }
    }
}

@Composable
fun PiWatchLogo(
    modifier: Modifier = Modifier
){
    Image(
        painter = painterResource(id = R.drawable.piwatch_logo),
        contentDescription = null,
        modifier = modifier,
        contentScale = ContentScale.Fit

    )
}

@Composable
fun iconWithText(
    text: String,
    icon: ImageVector
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Start
    ) {
       androidx.compose.material3.Icon(
           imageVector = icon,
           contentDescription = null,
           modifier = Modifier.weight(0.5f)
       )
        Text(
            text = text,
            modifier = Modifier.weight(2f),
            style = MaterialTheme.typography.titleSmall
            )
    }
}
@Composable
fun MyTextField(
    label: String,
    onValueChange: (String) -> Unit,
    value: String
) {
    OutlinedTextField(
        label = { Text(label) },
        value = value,
        onValueChange = {
            onValueChange(it)},
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
fun MyIconTextField(
    label: String,
    icon: ImageVector,
    onTextChange: (String) -> Unit,
    value: String,
    isError: Boolean,
    modifier : Modifier = Modifier
) {
    OutlinedTextField(
        label = { iconWithText(label, icon) },
        value = value,
        onValueChange =  onTextChange ,
        keyboardOptions = KeyboardOptions.Default,
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = MaterialTheme.shapes.medium,
        isError = isError
    )
}

@Composable
fun MyIconPasswordField(
    label: String,
    icon: ImageVector,
    onTextChange: (String) -> Unit,
    value: String,
    isError: Boolean
) {
    OutlinedTextField(
        label = { iconWithText(label, icon) },
        value = value,
        onValueChange =  onTextChange ,
        visualTransformation = PasswordVisualTransformation(),
        keyboardOptions = KeyboardOptions.Default,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
        shape = MaterialTheme.shapes.medium,
        isError = isError,
        textStyle = MaterialTheme.typography.titleSmall
    )
}


@Composable
fun LoginButton(
    text: String,
    color: Color,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(),
        colors = ButtonDefaults.buttonColors(
            color
        )
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.titleSmall
            )
    }
}
@Composable
fun LoginButtonWithIcon(
    text: String,
    color: Color,
    icon: Int,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(10.dp),
        colors = ButtonDefaults.buttonColors(
            color
        ),
        border = BorderStroke(2.dp, MaterialTheme.colorScheme.outline)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ){
            androidx.compose.material3.Icon(
                painter = painterResource(id = icon),
                contentDescription = null,
                tint = Color.Unspecified,
                modifier = Modifier.weight(1f)
                )
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onBackground,
                modifier = Modifier.weight(2f)
            )
        }
    }
}

@Composable
fun LoginButtonWithIcon2(
    text: String,
    color: Color,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        contentPadding = PaddingValues(horizontal = 15.dp, vertical = 5.dp, ),
        colors = ButtonDefaults.buttonColors(
            color
        ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            androidx.compose.material3.Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onPrimary,
            )
            Text(
                text = text,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onPrimary,
            )
        }
    }
}

@Composable
fun SmallMessage(
    text: String,
    onClick: (Int) -> Unit
) {
    ClickableText(
        text = AnnotatedString(text),
        style = MaterialTheme.typography.bodySmall,
        onClick = onClick
    )
}

@Composable
fun Loading(){
    CircularProgressIndicator(
        modifier = Modifier,
        color = MaterialTheme.colorScheme.secondary,
        trackColor = MaterialTheme.colorScheme.surfaceVariant,
    )
}

@Composable
fun ValidateError(
    text: Int
) {
    Text(
        text = stringResource(id = text),
        color = MaterialTheme.colorScheme.error,
        style = MaterialTheme.typography.titleSmall
    )
}

@Preview(showBackground = true)
@Composable
fun PiwatchLogoPrv() {
    PiWatchLogo(modifier = Modifier.height(40.dp))
}
/*@Preview(showBackground = true)
@Composable
fun TextComponentPreview(){
    TextComponent(text = "Hello")
}

@Preview(showBackground = true)
@Composable
fun PiWatchLogoPreview(){
    PiWatchLogo()
}

@Preview(showBackground = true)
@Composable
fun MyTextFieldPreview(
) {
    MyIconTextField("Username", Icons.Outlined.Person)
}

@Preview(showBackground = true)
@Composable
fun IconWithTextPreview(){
    iconWithText(text = "Username", icon = Icons.Outlined.Person)
}*/


