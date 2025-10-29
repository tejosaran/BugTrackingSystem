import { ComponentFixture, TestBed } from '@angular/core/testing';

import { Projectscomponent } from './projectscomponent';

describe('Projectscomponent', () => {
  let component: Projectscomponent;
  let fixture: ComponentFixture<Projectscomponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [Projectscomponent]
    })
    .compileComponents();

    fixture = TestBed.createComponent(Projectscomponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
