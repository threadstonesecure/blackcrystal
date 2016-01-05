import React from 'react';
import { Link } from 'react-router';
import { Navbar } from 'react-bootstrap';
import { Nav } from 'react-bootstrap';

const NAV_LINKS = {
  'overview': {
    link: '/overview',
    title: 'Overview'
  },
  'jobs': {
    link: '/jobs',
    title: 'Jobs'
  },
  'resources': {
    link: '/resources',
    title: 'Resources'
  },
  'diagnostic': {
    link: '/diagnostic.html',
    title: 'Diagnostic'
  }
};

const NavMain = React.createClass({
  propTypes: {
    activePage: React.PropTypes.string
  },

  render() {
    let brand = <Link to="/" className="navbar-brand">Black Crystal</Link>;
    let links = Object.keys(NAV_LINKS).map(this.renderNavItem);

    return (
      <Navbar staticTop
        componentClass="header"
        className="bs-docs-nav"
        role="banner"
      >
        <Navbar.Header>
          {brand}
        </Navbar.Header>
        <Navbar.Collapse className="bs-navbar-collapse" >
          <Nav role="navigation" id="top">
            {links}
          </Nav>
        </Navbar.Collapse>
      </Navbar>
    );
  },

  renderNavItem(linkName) {
    let link = NAV_LINKS[linkName];

    return (
        <li className={this.props.activePage === linkName ? 'active' : null} key={linkName}>
          <Link to={link.link}>{link.title}</Link>
        </li>
      );
  }
});

export default NavMain;
