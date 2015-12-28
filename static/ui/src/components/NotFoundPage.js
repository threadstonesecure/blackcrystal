import React from 'react';

import NavMain from './navigation/NavMain';
import PageHeader from './PageHeader';
import PageFooter from './PageFooter';

const NotFoundPage = React.createClass({
  render() {
    return (
        <div>
          <NavMain activePage="" />

          <PageHeader
            title="404"
            subTitle="Hmmm this is awkward." />

          <PageFooter />
        </div>
      );
  }
});

export default NotFoundPage;
