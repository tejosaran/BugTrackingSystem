import { Routes } from '@angular/router';
import { Projectscomponent } from './components/projectscomponent/projectscomponent';
import { Ticketscomponent } from './components/ticketscomponent/ticketscomponent';
import { Commentscomponent } from './components/commentscomponent/commentscomponent';
import { Ticketdetails } from './components/ticketdetails/ticketdetails';
import { Raiseticket } from './components/raiseticket/raiseticket';
import { Login } from './components/login/login';
import { Register } from './components/register/register';
import { AuthGuard } from './guards/auth.guard';

export const routes: Routes = [

{
  path:'',
  component:Login,
},
  
 {
    path:'users/:userId/projects',
    component: Projectscomponent,
    canActivate: [AuthGuard], 
 },


  { path: 'users/:userId/projects/:projectId/tickets', component: Ticketscomponent,canActivate: [AuthGuard],  },

  {
    path: 'users/:userId/projects/:projectId/tickets/:ticketId/comments',
    component: Commentscomponent,
    
    canActivate: [AuthGuard], 
  },

    {
    path: 'users/:userId/projects/:projectId/tickets/:ticketId',
    component: Ticketdetails,
    canActivate: [AuthGuard], 
  },
  {
    path: 'users/:userId/projects/:projectId/raiseticket',
    component: Raiseticket,
    canActivate: [AuthGuard], 
  },

 
   {
    path:'register',
    component:Register,
     
    

  },
];
