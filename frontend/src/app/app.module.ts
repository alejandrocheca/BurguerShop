import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { MenuComponent } from './main/menu/menu.component';

import { MessageModule } from 'primeng/message';
import { TabMenuModule } from 'primeng/tabmenu';
import { IngredientModule } from './ingredient/ingredient.module';
import { HamburguerModule } from './hamburguer/hamburguer.module';
import { CommentModule } from './comment/comment.module';
import { OrdersModule } from './orders/order.module';
import { UserModule } from './user/user.module';
import { FooterComponent } from './main/footer/footer.component';
import { ERROR_LEVEL, LoggerService } from 'src/lib/my-core/services/logger.service';
import { environment } from 'src/environments/environment';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { RouterModule } from '@angular/router';
import { CommonComponentModule } from './common';
import { SecurityModule } from './security';
import { LoginComponent } from './security/login-components/login.component';
import { AuthInterceptor } from './security/services/security.service';

@NgModule({
  declarations: [
    AppComponent,
    MenuComponent,
    FooterComponent,
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    MessageModule,
    TabMenuModule,
    CommentModule,
    OrdersModule,
    UserModule,
    HttpClientModule,
    RouterModule,
    CommonComponentModule,
    SecurityModule
  ],
  providers: [
    LoggerService,
    LoginComponent,
    { provide: ERROR_LEVEL, useValue: environment.ERROR_LEVEL  },
    { provide: HTTP_INTERCEPTORS, useClass: AuthInterceptor, multi: true, },  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
