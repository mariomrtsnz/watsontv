import { UserResponse } from './../../interfaces/user-response';
import { Router } from '@angular/router';
import { DialogGenreComponent } from './../../dialogs/dialog-genre/dialog-genre.component';
import { DialogDeleteUserComponent } from './../../dialogs/dialog-delete-user/dialog-delete-user.component';
import { DialogUserComponent } from './../../dialogs/dialog-user/dialog-user.component';
import { Title } from '@angular/platform-browser';
import { MatPaginator, MatSnackBar, MatTableDataSource, MatDialog } from '@angular/material';
import { Component, OnInit, ViewChild } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-user',
  templateUrl: './user.component.html',
  styleUrls: ['./user.component.scss']
})
export class UserComponent implements OnInit {

  displayedColumns: string[] = ['picture', 'name', 'email', 'actions'];
  dataSource;
  genres: any[] = [];
  alertMsg: string;

  @ViewChild(MatPaginator) paginator: MatPaginator;

  constructor(private titleService: Title, private snackBar: MatSnackBar,
    private userService: UserService, private router: Router, public dialog: MatDialog) { }

  ngOnInit() {
    this.titleService.setTitle('Genres');
    this.getAllUsers('Success retrieving items.');
  }

  getAllUsers(message: string) {
    this.userService.getAll().subscribe(receivedUsers => {
      this.dataSource = new MatTableDataSource(receivedUsers.rows);
      this.dataSource.paginator = this.paginator;
    }, error => {
      this.snackBar.open('There was an error loading the data.', 'Close', { duration: 3000 });
    });
  }

  applyFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  openDialogNewUser() {
    const newUserDialog = this.dialog.open(DialogUserComponent, { panelClass: 'add-dialog' });

    newUserDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'User created';
        this.getAllUsers(this.alertMsg);
      }
    });
  }

  openDialogDeleteUser(user: UserResponse) {
    const deleteUserDialog = this.dialog.open(DialogDeleteUserComponent,
      { panelClass: 'delete-dialog', data: { userId: user.id, userName: user.name } });

      deleteUserDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'User deleted';
        this.getAllUsers(this.alertMsg);
      }
    });
  }

  openDialogEditUser(user: UserResponse) {
    const updateUserDialog = this.dialog.open(DialogUserComponent, { panelClass: 'add-dialog', data: { user: user } });

    updateUserDialog.afterClosed().subscribe(result => {
      if (result === 'confirm') {
        this.alertMsg = 'User updated';
        this.getAllUsers(this.alertMsg);
      }
    });
  }

}
