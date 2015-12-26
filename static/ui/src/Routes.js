import React from 'react';
import {IndexRoute, Route} from 'react-router';

import HomePage from './HomePage';
import Jobs from './Jobs';
import JobDetails from './JobDetails';
import NotFoundPage from './NotFoundPage';
import Root from './Root';

export default (
  <Route path="/" component={Root}>
    <IndexRoute component={HomePage} />
    <Route path="jobs.html" component={Jobs} />
    <Route path="job/:name" component={JobDetails} />
    <Route path="*" component={NotFoundPage} />
  </Route>
);
