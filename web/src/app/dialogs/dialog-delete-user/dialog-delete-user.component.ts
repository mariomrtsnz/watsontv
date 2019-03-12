import { MAT_DIALOG_DATA, MatSnackBar, MatDialogRef } from '@angular/material';
import { Component, OnInit, Inject } from '@angular/core';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-dialog-delete-user',
  templateUrl: './dialog-delete-user.component.html',
  styleUrls: ['./dialog-delete-user.component.scss']
})
export class DialogDeleteUserComponent implements OnInit {

  elementId: string;
  elementName: string;
  checkedRobot: boolean;

  // tslint:disable-next-line:max-line-length
  constructor(@Inject(MAT_DIALOG_DATA) public data: any, public snackBar: MatSnackBar,
  private userService: UserService, public dialogRef: MatDialogRef<DialogDeleteUserComponent>) { }

  ngOnInit() {
    this.elementId = this.data.userId;
    this.elementName = this.data.userName;
  }

  captcha() {
    if (this.checkedRobot) {
      return true;
    } else {
      return false;
    }
  }

  close() {
    this.dialogRef.close('cancel');
  }

  delete() {
    this.userService.remove(this.elementId).subscribe(result => {
      this.dialogRef.close('confirm');
    }, error => this.snackBar.open('There was an error when trying to delete this route.', 'Close', {duration: 3000}));
  }

}
