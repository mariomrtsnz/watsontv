import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FlexLayoutModule } from '@angular/flex-layout';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';
// import { FileSelectDirective } from 'ng2-file-upload';

import { MaterialModule } from './../material-module';
import { DashboardRoutes } from './dashboard.routing';
import { MenuComponent } from './menu/menu.component';
import { UserComponent } from './user/user.component';
import { MediaComponent } from './media/media.component';

@NgModule({
  imports: [
    CommonModule,
    MaterialModule,
    FlexLayoutModule,
    FormsModule,
    ReactiveFormsModule,
    RouterModule.forChild(DashboardRoutes),
  ],
  declarations: [
    MenuComponent,
    UserComponent,
    // FileSelectDirective,
    MediaComponent,
    UserComponent
  ],
})

export class DashboardModule { }
