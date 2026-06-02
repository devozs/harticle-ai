"""Harticle training agent.

A lightweight worker that runs on a registered GPU/HPU box. It connects OUTBOUND
to harticle-management (register/heartbeat/claim/report), so it works behind NAT
or a corporate proxy without management ever dialing the box. It fine-tunes an
admin-selected HuggingFace model on the project's scraped data and reports live
progress, supporting cooperative stop and checkpoint-based resume.

Run it with:  ``python -m harticle.training``
"""
