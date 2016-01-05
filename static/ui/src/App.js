import React, { PropTypes } from 'react';
import DocumentTitle from 'react-document-title';
import HomePage from './pages/HomePage';

export default class App extends React.Component {
  static propTypes = {
    children: PropTypes.object
  };

  render() {
    return (
      <DocumentTitle title='Black Crystal'>
        {  
          this.props.children == null ?  <HomePage />  :  this.props.children
        }  
      </DocumentTitle>
    );
  }
}
