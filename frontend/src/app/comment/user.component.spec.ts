import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HamburgerComponent } from './user.component';

describe('HamburgerComponent', () => {
  let component: HamburgerComponent;
  let fixture: ComponentFixture<HamburgerComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [ HamburgerComponent ]
    })
    .compileComponents();
  });
  beforeEach(() => {
    fixture = TestBed.createComponent(HamburgerComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });
  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
